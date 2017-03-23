package WebSearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebSearchEngine {

    public static void main2(String[] args) {

        ArrayList<String> A = new ArrayList<>();
        ArrayList<String> B = new ArrayList<>();

        for (int i = 10; i < 20; i++) {
            A.add("" + i);
        }

        for (int i = 0; i < 10; i++) {
            B.add("" + i);
        }

        AlgebricSets as = new AlgebricSets(A, B);
        System.out.println(as.aDiffb());
    }

    public static void main(String[] args) throws IOException {
        Map<String, Document> m = new HashMap<>();

        Document Doc = Jsoup.connect("https://en.wikipedia.org/wiki/PageRank").get();

        m.put("https://en.wikipedia.org/wiki/PageRank", Doc);
        Indexer I = new Indexer(m);
        long startTime = System.currentTimeMillis();
        I.Execute();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }

    public static void main3(String[] args) {
        // TODO code application logic here

        /*
        URL_NORMALIZER n = new URL_NORMALIZER("https://wikipedia.org/wiki/Wikipedia:Featured_topics");
        
        String s = n.normalize();
         */
        Program crawler_and_indexer = new Program();

        crawler_and_indexer.run_search_Engine();
    }
}
