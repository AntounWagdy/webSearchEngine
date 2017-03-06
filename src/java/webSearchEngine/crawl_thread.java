/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSearchEngine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author Amr
 */
public class crawl_thread implements Runnable{
    webCrawler crawler;
    Thread  T;
    httpRequestHandler http_handler;
    Queue<URL> to_visit;
    
    
    public crawl_thread(webCrawler c)
    {
        crawler = c;
        http_handler = new httpRequestHandler();    
        T = new Thread(this);
    }
    
    
    void start()
    {
        T.start();
    }
    
    //criteria to stop crawling
    //1- queue is empty and no running threads
    
    @Override
    public void run() {
        
        URL top = crawler.popUrl();
        if(top == null)
        {
            crawler.finish();
            return; 
        }
        
        // 1- check satisfaction of all conditions , connection, html, robot.txt
        // 2- go and get the document and store it
        // 3- extract links and push them to the queue
        // 4- store the document
        
        //1
        if(! http_handler.check_type_html(top))  // condition not perfectly handled
        {
            crawler.finish();
            return;
        }
        
        if(crawler.get_visited(top))
        {
            return;
        }

        //2
        http_handler.downloadPage(top);

        
        String page_body = http_handler.getBody();
        crawler.add_page(top.toString(),page_body);  // return false if page is not added

        ArrayList<URL> links_in_page = (ArrayList)http_handler.getUrls();
        
        for(int i=0; i<links_in_page.size(); i++)
        {
            crawler.pushUrl(links_in_page.get(i));
        }

        crawler.start_new_threads();
        
    } 
}
