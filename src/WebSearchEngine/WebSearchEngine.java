/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;

 /*
 * @author Amr
 */
public class WebSearchEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   
        /*
        URL_NORMALIZER n = new URL_NORMALIZER("https://wikipedia.org/wiki/Wikipedia:Featured_topics");
        
        String s = n.normalize();
        */
        
       
        int _max_threads = 5;
        int _max_pages = 100;
        int save_rate = 20;
       
        Crawler_Data_loader loader = new Crawler_Data_loader();
        
        Queue<String> to_visit = loader.get_to_visit_urls();
        Set<String> visited = loader.get_visited_urls();
        Map<String, RobotTxtHandler> robots = loader.get_robot_handlers();
        int crawled_count = loader.get_crawled_count();
        
        if(to_visit.size() == 0)
        {
            to_visit.add("https://www.tutorialspoint.com");
            to_visit.add("https://en.wikipedia.org/wiki/Main_Page");
        }
       
       
        // create crawler
        webCrawler crawler = new webCrawler(_max_threads, _max_pages, save_rate);
        
        //set seeds for crawling
        crawler.set_Main_data(to_visit,visited, robots, crawled_count);
        
        
        crawler.start_threads();
        
        
        
    }
    
}
