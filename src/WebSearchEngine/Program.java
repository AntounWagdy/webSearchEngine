package WebSearchEngine;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.jsoup.nodes.Document;

public class Program {

    int _max_threads;
    queryManager qm;
    public Program(int t) {
        this._max_threads = t;
        qm = new queryManager();
    }

    void runSearchEngine() {
        //Crawling
        int _max_pages = 50;
        int save_rate = 10;

        Indexer_Ranker indexer = new Indexer_Ranker();
        Map<String, Document> pages;

        while (true) {
            // check database server first 
            try {
                databaseManager.getInstance();
            } catch (Exception e) {
                System.err.println("Check database server, no connection");
                break;
            }

            CrawlerDataLoader loader = new CrawlerDataLoader();

            Queue<String> to_visit = loader.getToVisitUrls();
            Set<String> visited = loader.getVisitedUrls();
            Map<String, RobotTxtHandler> robots = loader.getRobotHandlers();
            int crawled_count = loader.getCrawledCount();

            if (crawled_count < _max_pages) {

                // check internet connection
                httpRequestHandler h = new httpRequestHandler();
                if (!h.checkInternetConnectivity()) {
                    System.err.println("no internet connection , try again later");
                    break;
                }

                if (to_visit.isEmpty()) {
                    to_visit.add("https://wikipedia.org/wiki/Main_Page");
                    to_visit.add("http://dmoztools.net");
                }

                Boolean crawling_finished = Boolean.FALSE;   //to check if the crwaler has comletely finsihed or not
                // create crawler
                webCrawler crawler = new webCrawler(_max_threads, _max_pages, save_rate);

                //set crawling data
                crawler.setMainData(to_visit, visited, robots, crawled_count);

                crawling_finished = crawler.startThreads();

                if (!crawling_finished) // if crawler was interrupted
                {
                    System.out.println("error occurred, This crawling phase hasn't fisnished yet, start the program later");
                    break;
                }
                
                //1- remove duplicates from edge
                qm.delete_duplicated_edges();
                
                //2- remove edges between two nodes ,
                //one of them isn't included in the 5000 pages
                qm.delete_edges();
                
                //3-calculate in links
                qm.set_inlinks_count_for_downloaded_pages();
                
                
            } else {
                /*Indexer Part*/
                System.out.println("Indexer has started from scratch");
                pages = this.getPortionOfDownloadedPages();
                indexer.setTarget();

                int count = 0;
                while (!pages.isEmpty()) {
                    indexer.setDataMap(pages);
                    indexer.Execute_Indexer();
                    pages = this.getPortionOfDownloadedPages();
                    count += 200;
                    System.out.println(count + " pages successfully indexed");
                }
                indexer.Execute_Ranker();
                indexer.finish();
            }
            //break;
        }
    }

    Map<String, Document> getPortionOfDownloadedPages() {
        return new queryManager().selectAndDeletePagesbyLimit(200);
    }
}