package WebSearchEngine;

import java.util.ArrayList;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Indexer {
    Map<String, Document> dataMap;
    int in = 0 ;
    public Indexer(Map<String, Document> m) {
        this.dataMap = m;
    }

    public void Execute() {

        PorterStemmer PS = new PorterStemmer();
        for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
            ArrayList<String> res;

            /*1- Stem and insert the title*/
            String title = entry.getValue().title();
            res = PS.StemText(title);

            /*2- Stem any thing else*/
            String[] tags = {"ul", "ol", "table", "a", "h1", "h2", "h3", "h4", "h5", "h6", "p"};

            for (int i = 0; i < tags.length; i++) {
                /*Stem the above tags*/
                ArrayList<String> data = new ArrayList<>();
                Elements E = entry.getValue().select(tags[i]);
                for (Element e : E) {
                    data.add(e.text());
                }
                res = PS.StemArr(data);
                
                
                /*if database does not need update*/
                
                /*inserting the website*/
                queryManager Q = new queryManager();
                //Q.insertURL(i, title)
                for (String index : res) {
                    System.out.println(index);
                    /*Add or update database*/
                }
            }
            System.out.println(in);
        }
    }

}