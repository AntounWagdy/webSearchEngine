/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Amr
 */
public class Program {

    void run_search_Engine() {
        while (true) {            ///////////////////////////////////// crawler part ////////////////////////////////////////////////
            int _max_threads = 5;
            int _max_pages = 5000;
            int save_rate = 100;

            Crawler_Data_loader loader = new Crawler_Data_loader();

            Queue<String> to_visit = loader.get_to_visit_urls();
            Set<String> visited = loader.get_visited_urls();
            Map<String, RobotTxtHandler> robots = loader.get_robot_handlers();
            int crawled_count = loader.get_crawled_count();

            if (to_visit.size() == 0) {
                to_visit.add("https://www.tutorialspoint.com");
                to_visit.add("https://en.wikipedia.org/wiki/Main_Page");
            }

            Boolean crawling_finished = Boolean.FALSE;   //to check if the crwaler has comletely finsihed or not
            // create crawler
            webCrawler crawler = new webCrawler(_max_threads, _max_pages, save_rate, crawling_finished);

            //set crawling data
            crawler.set_Main_data(to_visit, visited, robots, crawled_count);

            crawling_finished = crawler.start_threads();

            if (crawling_finished == Boolean.FALSE) // if crawler was interrupted
            {
                System.out.println("error occurred, This crawling phase hasn't fisnished yet, start the program later");
                break;
            }

            Map<String, Document> pages = get_downloaded_pages();
            //flush_downloaded_pages();
            ///////////////////////////////////// indexer part ///////////////////////////////////////////////

            break;
        }
    }

    Map<String, Document> get_downloaded_pages() {
        queryManager qm = new queryManager();

        ResultSet rs = qm.select_downloaded_pages();

        Map<String, Document> url_Doc = new HashMap();

        try {
            while (rs.next()) {
                String url = rs.getString("Url");
                Document Doc = Jsoup.parse(rs.getString("page_content"));
                url_Doc.put(url, Doc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }

        return url_Doc;
    }

    void flush_downloaded_pages() {
        queryManager qm = new queryManager();

        qm.delete_all_from_downloaded_pages();
    }
}
