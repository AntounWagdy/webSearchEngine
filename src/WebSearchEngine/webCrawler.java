package WebSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

public class webCrawler {

    // Crawler's variables and counters
    int max_running_threads;
    int running_threads_count = 0;       //number of running threads
    int max_crawled_pages;               // max number of pages to be crawled
    int saving_rate;                     // number of pages to be crawled between two checkpoints
    int crawled_pages_count;             // number of crawled pages till now
    Boolean crawling_finished = false;   // if the crawling phase has completely finished, this boolean will be set "true"
    
    // Crawler's main Data dtructures
    Queue<String> to_visit;
    Set<String> visited;     
    Map<String, RobotTxtHandler> RobotHandlers;    // key = hostname , value = RobotTxtobject
    
    //to_visit: Queue that contains urls to be crawled in future
    
    //visited: Set that contains URLS popped from to_visit queue
    
    //RobotHandlers: Map , each host name has a robot handler object 
    //      contain all needed robotTxtHandlers for all websites,
    //      robot.txt will be fetched only one time per website, not for
    //      every page

    
    
    //Auxiliary data structures, used only to track changes between check points
    Set<String> visited_per_CP;                  // visited URLs to be added to database at each checkpoint
    Queue<String> to_visit_per_CP;               // URLS that are pushed in queue after the last checkpoint ,but not popped
    Map<String, RobotTxtHandler> Robots_per_CP;  // handlers inserted in Robot handler map after the last checkpoint
    Map<String, Document> crawled_per_CP;        // crawled pages inserted in crawled_pages map after last checkpoint


    public webCrawler(int _max_threads, int _max_pages, int save_rate) {
        
        max_running_threads = _max_threads;
        max_crawled_pages = _max_pages;
        saving_rate = save_rate;

        //initialize Main data structure
        to_visit = new ConcurrentLinkedQueue();
        visited = new ConcurrentSkipListSet();
        RobotHandlers = new ConcurrentHashMap();

        //initialize Auxiliary Data structure
        visited_per_CP = new ConcurrentSkipListSet();
        to_visit_per_CP = new ConcurrentLinkedQueue();
        Robots_per_CP = new ConcurrentHashMap();
        crawled_per_CP = new ConcurrentHashMap();
    }

    boolean checkRobotTxt(String urlString) {

        synchronized (Robots_per_CP) {
            try {
                URL url = new URL(urlString);
                URL base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));

                if (!RobotHandlers.containsKey(base.getHost())) //first create handler if it does not exist
                {
                    RobotTxtHandler H = new RobotTxtHandler(base);
                    RobotHandlers.put(base.getHost(), H);
                    Robots_per_CP.put(base.getHost(), H);
                }

                RobotTxtHandler RH = RobotHandlers.get(base.getHost());

                /*return true if it is not in disallowed or even if it is in
                 *disallowed but allowed for this agent
                 */
                ArrayList<String> Disallowed = RH.getDisallowed();
                
                for(String dis:Disallowed){
                    if(urlString.matches(dis))
                        return false;
                }
                
                return true;

            } catch (MalformedURLException ex) {
                Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;//wrong URL
        }

    }

    // set main data before crawling, if you have a saved version
    void set_Main_data(Queue<String> TV, Set<String> V, Map<String, RobotTxtHandler> RH, int cc) {
        to_visit = TV;
        visited = V;
        RobotHandlers = RH;
        crawled_pages_count = cc;
    }

    // used to insert a page in carawled_per_CP Map, preparing it to be inserted in DB
    boolean add_page(String url, Document page) {

        synchronized (crawled_per_CP) {

            if (crawled_pages_count < max_crawled_pages) {

                crawled_per_CP.put(url, page);
                crawled_pages_count++;
                System.out.println(url);
                System.out.println("crawl count =" + crawled_pages_count);

                //if you crawled the agreed upon number "saving rate" , then save data in DB            
                if (crawled_per_CP.size() == saving_rate) {

                    synchronized (to_visit_per_CP) {
                        synchronized (visited_per_CP) {
                            synchronized (Robots_per_CP) {
                                try {
                                    System.out.println("Waiting ...");
                                    update_DB();
                                    System.out.println("DB updated");
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
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

    //used to push urls into to_visit queue, to_visit_per_CP
    public boolean pushUrl(String url) {

        URL_NORMALIZER normalizer = new URL_NORMALIZER(url);
        try {
            url = new URL(normalizer.normalize()).toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(crawl_thread.class.getName()).log(Level.SEVERE, null, ex);
        }

        synchronized (to_visit) {
            synchronized (visited) {
                synchronized (to_visit_per_CP) {

                    if (!to_visit.contains(url) && !visited.contains(url)) {
                        to_visit_per_CP.add(url);
                        return to_visit.add(url);
                    }
                    return false;
                }
            }
        }
    }

    // used to pop a url from to_visit queue to start its processing in a thread
    String popUrl() {  

        synchronized (to_visit) {
            synchronized (visited) {
                synchronized (visited_per_CP) {
                    String url = to_visit.poll();
                    if (url != null) {
                        visited.add(url);
                        visited_per_CP.add(url);
                    }
                    return url;
                }
            }
        }
    }

    //called by a thread when it finishes its work
    synchronized void finish() {
        running_threads_count--;
        if (running_threads_count == 0) {

            System.out.println("5lass ,  raowa7");
            System.out.println("size of queue = " + to_visit.size());
            System.out.println(crawled_pages_count);
        }
    }

    // used to start The crawling threads
    boolean start_threads() {

        Thread ts[] = new Thread[max_running_threads];

        for (int i = 0; i < max_running_threads; i++) {
            ts[i] = new crawl_thread(this);
            ts[i].start();
            running_threads_count++;
            System.out.println("new thread created: " + running_threads_count);
        }

        for (int i = 0; i < max_running_threads; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return crawling_finished;
    }

    
    // used to update the DB at each CP
    void update_DB() throws SQLException {
        queryManager qm = new queryManager();

        //////////////////// update visited set in the DB /////////////////////////
        qm.optimizedInsert_into_visited(visited_per_CP);

        /////////////update to_visit queue in the DB/////////////////////
        qm.optimizedInsert_into_to_visit(to_visit_per_CP);
        qm.optimized_delete_from_to_visit(visited_per_CP);

        ///////////////////update Robot handlers in DB/////////////////
        if (!Robots_per_CP.isEmpty()) {
            qm.optimizedInsert_into_robots(Robots_per_CP);
        }

        //////////////////update downloaded pages in DB//////////////////
        qm.optimizedInsert_into_Downloaded_page(crawled_per_CP);


        //clear auxiliary data 
        visited_per_CP.clear();
        to_visit_per_CP.clear();
        Robots_per_CP.clear();
        crawled_per_CP.clear();
    }
}
