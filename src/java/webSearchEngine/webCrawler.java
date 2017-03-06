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
    Queue<URL> to_visit;
    Set<URL> visited;     // elemnts here means that those URLs are already popped out from queue
    Map<String, String> crawled_pages;   // map key = URL,, value= page text 

    Map<String, RobotTxtHandler> RobotHandlers;    // key = hostname , value = RobotTxtobject ,
    // contain all needed robotTxtHandlers for all websites,
    // robot.txt will be fetched only one time per website, not for
    // every page

    public webCrawler(int _max_threads, int _max_pages) {
        max_threads = _max_threads;
        max_crawled_count = _max_pages;
        to_visit = new LinkedList();
        visited = new HashSet();
        RobotHandlers = new HashMap();
    }

    // implement file handlers here
    // implement URL normalizer here
    synchronized boolean checkRobotTxt(String urlString) {
        try {
            URL url = new URL(urlString);
            URL base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));

            if (!RobotHandlers.containsKey(base)) //first create handler if it does not exist
            {
                RobotHandlers.put(base.toString(), new RobotTxtHandler(base));
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

    synchronized void add_page(String url, String page) {
        crawled_pages.put(url, page);
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

    // this function should handle if URL is enqueued or visited don't push in queue
    public synchronized boolean pushUrl(URL url) {
        if (!to_visit.contains(url) && !visited.contains(url)) {
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
        }

        return url;
    }

    // N.B. : we can recieve here Thread ID to finish anything related to the thread
    synchronized void finish() {
        running_ths--;
    }

    synchronized void start_new_threads() {
        //getting the allowable number of threads that can be created
        Integer remaining = max_threads - running_ths;

        remaining = (to_visit.size() < remaining)? to_visit.size() : remaining;

        if (remaining > max_crawled_count - visited.size()) {
            remaining = max_crawled_count - visited.size();
        }

        for (int i = 0; i < remaining; i++) {
            crawl_thread T = new crawl_thread(this);
            T.start();
            running_ths++;
        }
    }
}
