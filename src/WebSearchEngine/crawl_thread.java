package WebSearchEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;


/**
 *
 * @author Amr
 */
public class crawl_thread extends Thread{
    webCrawler crawler;
    httpRequestHandler http_handler;
    Queue<URL> to_visit;
    
    
    public crawl_thread(webCrawler c)
    {
        crawler = c;
        http_handler = new httpRequestHandler();    
    }
    
    
   // @Override
    /*    public void start()
    {
    //super.start();
    }*/
    
    //criteria to stop crawling
    //1- queue is empty and no running threads
    
    @Override
    public void run() {
        
    
        while(true)    
        {  
            //1- check internet connectivity, if not connected work should be finished
            if(!http_handler.check_connectivity())
            {
                break;
            }

          
            //2- pop from queue
            String top = crawler.popUrl();
            if(top == null)
            {
                //System.out.println("refused 1");
                continue;
            }

            //3- check if doc is html
            if(! http_handler.check_type_html(top)){ 
                System.out.println("refused 2");
                continue;
              //  crawler.finish();
              //  return;
            }



            //4- check robot disallow
            if(!crawler.checkRobotTxt(top.toString()))
            {
                System.out.println("refused 3");
                continue;
                //crawler.finish();
                //return;
            }






            //5- download, extract page body and save it
            boolean downloaded = http_handler.downloadPage(top);
            if(! downloaded)
            {
                System.out.println("refused 4");
                    continue;
                //crawler.finish();
                //return;
            }
            Document page_body = http_handler.get_doc();
            boolean added = crawler.add_page(top.toString(),page_body);  // return false if page is not added

            // if !added means that connection may be lost or crawling limit is reached , so we have to break
            if(!added)
            {
                //System.out.println("");
                break;
                //crawler.finish();
                //return;
            }




            //6- extract links and push them to crawling queue
            ArrayList<URL> links_in_page = (ArrayList)http_handler.getUrls();
            //System.out.println("sizeeee: "+ links_in_page.size());
            for(int i=0; i<links_in_page.size(); i++){
                if(http_handler.valid(links_in_page.get(i)))
                    crawler.pushUrl(links_in_page.get(i).toString());
            }


            //System.out.println("Da5alt hena fahem");

        }
            crawler.finish();
    } 
}