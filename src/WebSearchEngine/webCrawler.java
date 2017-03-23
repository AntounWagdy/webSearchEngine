package WebSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;
import org.jsoup.nodes.Document;

/**
 *
 * @author Antoun
 */
public class webCrawler {

    // defining crawler's data
    int max_threads;
    int running_ths = 0;    //number of running threads
    int max_crawled_count;  // max to be crawled
    int max_crawl_per_checkpt;  // number of pages to be crawled between two checkpoints
    int crawling_count;    // number of crawled pages till now
    Boolean crawling_finished;   // if the crawling phase has completely finished, this boolean will be set
    Queue<String> to_visit;
    Set<String> visited;     // elemnts here means that those URLs are already popped out from queue
//    Map<String, Document> crawled_pages;   // map key = URL,, value= page text 

    Map<String, RobotTxtHandler> RobotHandlers;    // key = hostname , value = RobotTxtobject ,
    // contain all needed robotTxtHandlers for all websites,
    // robot.txt will be fetched only one time per website, not for
    // every page

    //Auxiliary data structures, used only to track changes between check points
    Set<String> visited_insert;   // visited URLs to be added to database at each checkpoint
    Queue<String> to_visit_insert;    // URLS that are pushed in queue after the last checkpoint ,but not popped
    //  Queue<String> to_visit_delete;    // URLS that are popped after the last checkpoint, you have to remove them from the DB
    Map<String, RobotTxtHandler> Robots_insert;  // handlers inserted in Robot handler map after the last checkpoint
    Map<String, Document> crawled_insert;  // crawled pages inserted in crawled_pages map after last checkpoint

    Thread threads[];

    public webCrawler(int _max_threads, int _max_pages, int save_rate, Boolean crawling_finished) {
        //queryManager qm = new queryManager();
        max_threads = _max_threads;
        max_crawled_count = _max_pages;
        max_crawl_per_checkpt = save_rate;
        this.crawling_finished = crawling_finished;

        //initialize Main data structure
        to_visit = new ConcurrentLinkedQueue();
        visited = new ConcurrentSkipListSet();
        RobotHandlers = new ConcurrentHashMap();
//        crawled_pages = new ConcurrentHashMap();

        //initialize Auxiliary Data structure
        visited_insert = new ConcurrentSkipListSet();
        to_visit_insert = new ConcurrentLinkedQueue();
        //    to_visit_delete = new LinkedList();
        Robots_insert = new ConcurrentHashMap();
        crawled_insert = new ConcurrentHashMap();
    }

    boolean checkRobotTxt(String urlString) {

        synchronized (Robots_insert) {
            try {
                URL url = new URL(urlString);
                URL base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));

                if (!RobotHandlers.containsKey(base.toString())) //first create handler if it does not exist
                {
                    RobotTxtHandler H = new RobotTxtHandler(base);
                    RobotHandlers.put(base.toString(), H);
                    Robots_insert.put(base.toString(), H);
                }

                RobotTxtHandler RH = RobotHandlers.get(base.toString());

                /*return true if it is not in disallowed or even if it is in
             *disallowed but allowed for this agent
                 */
                return !RH.getDisallowed().contains(urlString);

            } catch (MalformedURLException ex) {
                Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;//wrong URL
        }

    }

    void set_Main_data(Queue<String> TV, Set<String> V, Map<String, RobotTxtHandler> RH, int cc) {
        to_visit = TV;
        visited = V;
        RobotHandlers = RH;
        crawling_count = cc;
    }

    boolean add_page(String url, Document page) {

        synchronized (crawled_insert) {

            if (crawling_count < max_crawled_count) {

                crawled_insert.put(url, page);
//                crawled_pages.put(url, page);

                crawling_count++;
                System.out.println(url);
                //System.out.println(crawled_pages.size());
                System.out.println("crawl count =" + crawling_count);

                //if you crawled the agreed upon number "max_crawl_per_checkpt" , then save data in DB            
                if (crawled_insert.size() == max_crawl_per_checkpt) {

                    synchronized (to_visit_insert) {
                        synchronized (visited_insert) {
                            synchronized (Robots_insert) {
                                try {
                                    System.out.println("Waiting ...");
                                    //Thread.sleep(3000);
                                    update_DB();
                                    System.out.println("DB updated");
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }

                //System.out.println("page added "+ crawled_pages.size());
                return true;
            } else {
                crawling_finished = Boolean.TRUE;

                queryManager qm = new queryManager();
                qm.Flush_visited();
                qm.Flush_to_visit();
                qm.Flush_robot_2();
                qm.Flush_robot_1();

            }
            return false;
        }
    }

    /*
    public int get_crawled_pages_count() {
        return visited.size();
    }

    public int get_max_crawled_count() {
        return max_crawled_count;
    }

    public boolean get_visited(URL url) {
        return visited.contains(url);
    }
     */

//    synchronized boolean set_visited(URL url) {
//        if (!visited.contains(url)) {
//            return visited.add(url);
//        }
//        return false;
//    }
    public boolean pushUrl(String url) {  // sync

        URL_NORMALIZER normalizer = new URL_NORMALIZER(url);
        try {
            url = new URL(normalizer.normalize()).toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(crawl_thread.class.getName()).log(Level.SEVERE, null, ex);
        }

        synchronized (to_visit) {
            synchronized (visited) {
                synchronized (to_visit_insert) {

                    if (!to_visit.contains(url) && !visited.contains(url)) {
                        to_visit_insert.add(url);
                        return to_visit.add(url);
                    }
                    return false;
                }
            }
        }
    }

    String popUrl() {  // sync

        synchronized (to_visit) {
            synchronized (visited) {
                synchronized (visited_insert) {
                    String url = to_visit.poll();
                    if (url != null) {
                        visited.add(url);
                        visited_insert.add(url);
                    }
                    return url;
                }
            }
        }
    }

    // N.B. : we can recieve here Thread ID to finish anything related to the thread
    synchronized void finish() {
        running_ths--;
        if (running_ths == 0) {

            System.out.println("5lass ,  raowa7");
            System.out.println("size of queue = " + to_visit.size());
            System.out.println(crawling_count);
        }
    }

    // instead of start_new_threads
    boolean start_threads() {

        Thread ts[] = new Thread[max_threads];

        for (int i = 0; i < max_threads; i++) {
            ts[i] = new crawl_thread(this);
            ts[i].start();
            running_ths++;
            System.out.println("new thread created: " + running_ths);
        }

        for (int i = 0; i < max_threads; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return crawling_finished;
    }

    // to be implemented by luca 
    void update_DB() throws SQLException {
        queryManager qm = new queryManager();

        //////////////////// visited /////////////////////////
        String urlll;
        for (Iterator<String> it = visited_insert.iterator(); it.hasNext();) {
            urlll = it.next();
            qm.insertURLintoVisited(urlll);
        }
        //Don't flush now it's needed later
        //visited_insert.clear();  //flush (delete) visited set

        /////////////to_visit_insert/////////////////////
        String uuurll;
        for (int i = 0; i < to_visit_insert.size(); i++) {       // why +1  ?????
            uuurll = to_visit_insert.remove();
            //System.out.println(uuurll);
            qm.insert_URL_into_to_visit(uuurll);

        }
        ////to_visit_insert.clear();

        //////////////////to_visit_delete///////////////
        String uuurl;

        Iterator<String> it = visited_insert.iterator();
        while (it.hasNext()) {
            uuurl = it.next();
            qm.deleteURLfrom_to_visit(uuurl);
        }

//        for(int i=0; i<visited_insert.size(); i++)    // why +1  ?????
//         {
//             uuurl=visited_insert.remove().toString();
//             //System.out.println(uuurl);
//             qm.deleteURLfrom_to_visit(uuurl);
//             
//         }
        ////to_visit_delete.clear();
        ///////////////////Robot handler/////////////////
        String host;
        RobotTxtHandler robot_handler;

        for (Map.Entry<String, RobotTxtHandler> entry : Robots_insert.entrySet()) {
            host = entry.getKey();
            robot_handler = entry.getValue();
            qm.insertinto_robot_handler_1(host, robot_handler.getCrawlDelay());

            ArrayList<String> disallowed = robot_handler.getDisallowed();

            String url;
            for (int i = 0; i < disallowed.size(); i++) {
                url = disallowed.get(i);
                qm.insertinto_robot_handler_2(host, url);
            }
        }
        //// Robots_insert.clear();

        //////////////////downloade page//////////////////
        String url_page;
        Document content_page;

        for (Map.Entry<String, Document> entry : crawled_insert.entrySet()) {
            url_page = entry.getKey();
            content_page = entry.getValue();
            qm.insertinto_downloaded_page(url_page, content_page);
        }
        //// crawled_insert.clear();

        visited_insert.clear();
        to_visit_insert.clear();
        Robots_insert.clear();
        crawled_insert.clear();
    }//end update
}
