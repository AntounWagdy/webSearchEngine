package WebSearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebSearchEngine {

  /*  public static void main2(String[] args) {

        ArrayList<String> A = new ArrayList<>();
        ArrayList<String> B = new ArrayList<>();

        for (int i = 10; i < 20; i++) {
            A.add("" + i);
        }

        for (int i = 0; i < 10; i++) {
            B.add("" + i);
        }
        System.out.println(AlgebricSets.aDiffb(A,B));
    }

    public static void main(String[] args) throws IOException {
        Map<String, Document> m = new HashMap<>();

        String x ="https://en.wikipedia.org/wiki/Parthenon" ;
        Document Doc = Jsoup.connect(x).get();

        
        m.put(x, Doc);
        Indexer I = new Indexer(m);
        long startTime = System.currentTimeMillis();
        I.Execute();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }
*/
    public static void main(String[] args) {
        // TODO code application logic here

        /*
        URL_NORMALIZER n = new URL_NORMALIZER("https://wikipedia.org/wiki/Wikipedia:Featured_topics");
        
        String s = n.normalize();
         */
        Program crawler_and_indexer = new Program();

        crawler_and_indexer.run_search_Engine();
    }
}
