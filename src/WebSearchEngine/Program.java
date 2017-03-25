package WebSearchEngine;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.jsoup.nodes.Document;

public class Program {

    int _max_threads;

    public Program(int t) {
        this._max_threads = t;
    }

    void runSearchEngine() {

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
            int _max_pages = 5000;
            int save_rate = 200;

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
                    //to_visit.add("https://www.tutorialspoint.com");
                    to_visit.add("https://en.wikipedia.org/wiki/Main_Page");
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
            } else {
                /*Indexer Part*/
                pages = this.getPortionOfDownloadedPages();
                indexer.setTarget();

                int count = 0;
                while (!pages.isEmpty()) {
                    indexer.setDataMap(pages);
                    indexer.Execute();
                    pages = this.getPortionOfDownloadedPages();
                    count += 200;
                    System.out.println(count + " pages successfully indexed");
                }
                indexer.finish();
            }
            //break;
        }
    }

    Map<String, Document> getPortionOfDownloadedPages() {
        return new queryManager().selectAndDeletePagesbyLimit(200);
    }
}
