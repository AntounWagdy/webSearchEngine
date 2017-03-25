package WebSearchEngine;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.jsoup.nodes.Document;

public class Program {

    void run_search_Engine() {

        Indexer indexer = new Indexer();
        Map<String, Document> pages;

        while (true) {
            
            // check database server first 
            try {
                databaseManager DBM = databaseManager.getInstance();
            } catch (Exception e) {
                System.err.println("Check database server, no connection");
                break;
            }

            //Crawling
            int _max_threads = 5;
            int _max_pages = 5000;
            int save_rate = 200;

            Crawler_Data_loader loader = new Crawler_Data_loader();

            Queue<String> to_visit = loader.get_to_visit_urls();
            Set<String> visited = loader.get_visited_urls();
            Map<String, RobotTxtHandler> robots = loader.get_robot_handlers();
            int crawled_count = loader.get_crawled_count();

            
            if(crawled_count < _max_pages)
            {
                
                // check internet connection
                httpRequestHandler h = new httpRequestHandler();
                if (!h.check_Internet_connectivity()) {
                    System.err.println("no internet connection , try again later");
                    break;
                }

                if (to_visit.isEmpty()) {
                    //to_visit.add("https://www.tutorialspoint.com");
                    to_visit.add("https://en.wikipedia.org/wiki/Main_Page");
                    to_visit.add("http://dmoztools.net");
                }
            
                Boolean crawling_finished = Boolean.FALSE;   //to check if the crwaler has comletely finsihed or not
                // create crawler
                webCrawler crawler = new webCrawler(_max_threads, _max_pages, save_rate);

                //set crawling data
                crawler.set_Main_data(to_visit, visited, robots, crawled_count);

                crawling_finished = crawler.start_threads();

                if (!crawling_finished) // if crawler was interrupted
                {
                    System.out.println("error occurred, This crawling phase hasn't fisnished yet, start the program later");
                    break;
                }
            }
            else{
                /*Indexer Part*/
                pages = this.get_portion_of_downloaded_pages();
                indexer.setTarget();

                int count= 0;
                while (!pages.isEmpty()) {
                    indexer.setDataMap(pages);
                    indexer.Execute();
                    pages = this.get_portion_of_downloaded_pages();
                    count+=200;
                    System.out.println(count+" pages successfully indexed");
                }
            }
            //break;
        }
    }

    Map<String, Document> get_portion_of_downloaded_pages() {
        return new queryManager().select_and_delete_pages_by_limit(200);
    }
}
