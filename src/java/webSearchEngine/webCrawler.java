package webSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    Queue<URL> to_visit;
    Set<URL> visited;     // elemnts here means that those URLs are already popped out from queue
    Map<String, String> crawled_pages;   // map key = URL,, value= page text 

    Map<String, RobotTxtHandler> RobotHandlers;    // key = hostname , value = RobotTxtobject ,
    // contain all needed robotTxtHandlers for all websites,
    // robot.txt will be fetched only one time per website, not for
    // every page

    
    
    //Auxiliary data structures, used only to track changes between check points
    Set<URL> visited_insert;   // visited URLs to be added to database at each checkpoint
    Queue<URL> to_visit_insert;    // URLS that are pushed in queue after the last checkpoint ,but not popped
    Queue<URL> to_visit_delete;    // URLS that are popped after the last checkpoint, you have to remove them from the DB
    Map<String, String>Robots_insert;  // handlers inserted in Robot handler map after the last checkpoint
    Map<String, String>crawled_insert;  // crawled pages inserted in crawled_pages map after last checkpoint
    
    

public webCrawler(int _max_threads, int _max_pages, int save_rate) {
        max_threads = _max_threads;
        max_crawled_count = _max_pages;
        max_crawl_per_checkpt = save_rate;
        
        //initialize Main data structure
        to_visit = new LinkedList();
        visited = new HashSet();
        RobotHandlers = new HashMap();
        crawled_pages = new HashMap();
        
        //initialize Auxiliary Data structure
        visited_insert = new HashSet();
        to_visit_insert = new LinkedList();
        to_visit_delete = new LinkedList();
        Robots_insert = new HashMap();
        crawled_insert = new HashMap();
    }


    synchronized boolean checkRobotTxt(String urlString) {
        try {
            URL url = new URL(urlString);
            URL base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));

            if (!RobotHandlers.containsKey(base)) //first create handler if it does not exist
            {
                RobotTxtHandler H = new RobotTxtHandler(base);
                RobotHandlers.put(base.toString(), H);
                Robots_insert.put(urlString, H.toString()); 
            }

            RobotTxtHandler RH = RobotHandlers.get(base.toString());

            /*return true if it is not in disallowed or even if it is in
             *disallowed but allowed for this agent
             */
            return RH.getAllowed().contains(url) || !RH.getDisallowed().contains(url);

        } catch (MalformedURLException ex) {
            Logger.getLogger(webCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;//wrong URL
    }
    
    // use this function to set Main data if you have a saved version
    void set_Main_data(LinkedList<URL> TV, HashSet<URL> V, HashMap<String, String> RH, HashMap<String, String>CP)
    {
        to_visit = TV;
        visited = V;
        crawled_pages = CP;
        
        // this loop to convert robot_string to robotTxtHandler
        for (Map.Entry<String, String> entrySet : RH.entrySet()) {
            String key = entrySet.getKey(); // host
            String value = entrySet.getValue(); // robot data as string
            
            RobotTxtHandler h = new RobotTxtHandler(value);
            RobotHandlers.put(key, h);
        }
    }
      
    
    //use this function for the first time crawl ,i.e. you don't have a sved version of main data in DB  
    void set_Queue(Queue<URL> Q)//initialize queue
    {
        to_visit = Q;
    }
    

    synchronized boolean add_page(String url, String page) {   

  
        if(crawled_pages.size()< max_crawled_count)
        {
            
            crawled_insert.put(url, page);
            crawled_pages.put(url, page);
            
            // if you crawled the agreed upon number "max_crawl_per_checkpt" , then save data in DB            
            if(crawled_insert.size() == max_crawl_per_checkpt)
            {
                update_DB();
            }
            
            System.out.println(url);
            System.out.println(crawled_pages.size());
            //System.out.println("page added "+ crawled_pages.size());
            return true;
        }
        return false;
    }

    public int get_crawled_pages_count() {
        return visited.size();
    }

    public int get_max_crawled_count() {
        return max_crawled_count;
    }

    public boolean get_visited(URL url) {
        return visited.contains(url);
    }

    synchronized boolean set_visited(URL url) {
        if (!visited.contains(url)) {
            return visited.add(url);
        }
        return false;
    }

    public synchronized boolean pushUrl(URL url) {
        
        URL_NORMALIZER normalizer = new URL_NORMALIZER(url);
        try {
            url = new URL(normalizer.normalize());
        } catch (MalformedURLException ex) {
            Logger.getLogger(crawl_thread.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if (!to_visit.contains(url) && !visited.contains(url)) {            
                   to_visit_insert.add(url);
            return to_visit.add(url);
        }
        return false;
    }

  synchronized URL popUrl() {
        URL url = to_visit.poll();

        /*
    if URL is popped from queue, it should be marked as visited
    so as not to be pushed again into queue    
         */
        if (url != null) {
            visited.add(url);
            visited_insert.add(url);
            to_visit_delete.add(url);
        }

        return url;
    }
  
    // N.B. : we can recieve here Thread ID to finish anything related to the thread
   synchronized void finish() {
        running_ths--;
        if(running_ths ==0)
        {
            System.out.println("5lass ,  raowa7");
            System.out.println("size of queue = "+ to_visit.size());
            System.out.println(crawled_pages.size());
        }
    }
   
   // instead of start_new_threads
  synchronized void start_threads()
    {
        for (int i = 0; i < max_threads; i++) {
            crawl_thread T = new crawl_thread(this);
            T.start();
            running_ths++;
            System.out.println("new thread created: "+ running_ths);
        }        
    }

  // to be implemented by luca 
  void update_DB()
    {
        // 
        // update DB 
        // flush Aux data structures
    }
}
