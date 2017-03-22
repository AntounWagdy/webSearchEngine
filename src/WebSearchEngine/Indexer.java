package WebSearchEngine;

import java.util.ArrayList;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Indexer {

    Map<String, Document> dataMap;

    public Indexer(Map<String, Document> m) {
        this.dataMap = m;
    }

    public void Execute() {
        queryManager Q = new queryManager();
        PorterStemmer PS = new PorterStemmer();
        int doc_id;
        for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
            Q.insertinto_document(entry.getKey());
            doc_id = Q.getId_from_document(entry.getKey());
            ArrayList<String> res;
            /*1- Stem and insert the title*/
            String title = entry.getValue().title();
            res = PS.StemText(title);

            for (int i = 0; i < res.size(); i++) {
                Q.insertinto_doc_words(doc_id, res.get(i), i, "title");
            }

            /*2- Stem any thing else*/
            String[] tags = {"ul", "ol", "table", "a", "h1", "h2", "h3", "h4", "h5", "h6", "p"};

            for (int i = 0; i < tags.length; i++) {
                /*Stem the above tags*/
                ArrayList<String> data = new ArrayList<>();
                Elements E = entry.getValue().select(tags[i]);
                for (int k = 0; k < E.size(); k++) {
                    res = PS.StemText(E.get(k).text());
                    if(!res.isEmpty())
                        Q.optimizedInsertIntoDocWords(doc_id, res, tags[i]+" "+k);
                }
                System.out.println(tags[i] + " has finished B-)");
            }
        }
    }

}
