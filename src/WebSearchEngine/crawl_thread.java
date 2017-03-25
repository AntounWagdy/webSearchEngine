package WebSearchEngine;

import java.net.URL;
import java.util.ArrayList;
import org.jsoup.nodes.Document;


public class crawl_thread extends Thread{
    webCrawler crawler;
    httpRequestHandler http_handler;
    
    
    public crawl_thread(webCrawler c)
    {
        crawler = c;
        http_handler = new httpRequestHandler();    
    }
    
    
    @Override
    public void run() {
        
    
        while(true)    
        {  
            
            //1- check internet connectivity, if not connected work should be finished
            if(!http_handler.check_Internet_connectivity())
            {
                System.out.println("no Internet Connection!");
                break;
            }

          
            //2- pop from queue
            String top = crawler.popUrl();
            if(top == null)
            {
                continue;
            }

            
            //3- check server status
            if(! http_handler.check_server_response(top)){ 
                continue;
            }



            //4- check robot disallow
            if(!crawler.checkRobotTxt(top)){
                System.out.println("refused in the Robots.txt");
                continue;
            }






            //5- download, extract page body and save it
            boolean downloaded = http_handler.downloadPage(top);
            if(! downloaded)
            {
                System.out.println("Error occured while downloading");
                continue;
            }
            Document page_body = http_handler.get_doc();
            boolean added = crawler.add_page(top.toString(),page_body);  // return false if page is not added

            // if !added means that crawling limit is reached , so we have to break
            if(!added)
            {
                break;
            }




            //6- extract links and push them to crawling queue
            ArrayList<URL> links_in_page = (ArrayList)http_handler.getUrls();
            //System.out.println("sizeeee: "+ links_in_page.size());
            for(int i=0; i<links_in_page.size(); i++){
                if(http_handler.valid(links_in_page.get(i)))
                {
                    crawler.pushUrl(links_in_page.get(i).toString());
                }
            }
        }
            crawler.finish();
    } 
}