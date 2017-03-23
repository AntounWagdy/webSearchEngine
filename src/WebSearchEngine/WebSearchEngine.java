/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

 /*
 * @author Amr
 */
public class WebSearchEngine {

    /**
     * @param args the command line arguments
     */
    
//    public static void main(String [] args) throws IOException
//    {
//        Map<String,Document> m  = new HashMap<String, Document>();
//        
//        Document Doc = Jsoup.connect("https://en.wikipedia.org/wiki/PageRank").get();
//      
//        m.put("https://en.wikipedia.org/wiki/PageRank", Doc);
//        Indexer I = new Indexer (m);
//        I.Execute();
//    }
    public static void main2(String[] args) {
        // TODO code application logic here
   
        /*
        URL_NORMALIZER n = new URL_NORMALIZER("https://wikipedia.org/wiki/Wikipedia:Featured_topics");
        
        String s = n.normalize();
        */
        
        Program crawler_and_indexer = new Program();
        
        crawler_and_indexer.run_search_Engine();
        
        
    }
    
}
