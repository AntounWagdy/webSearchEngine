package WebSearchEngine;

import java.util.ArrayList;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Indexer {

    Map<String, Document> dataMap;
    queryManager Q;
    int targetDatabase;

    public Indexer() {
        Q = new queryManager();
    }

    void setTarget() {
        targetDatabase = (Q.getCountDocument1() == 0) ? 1 : 2;
        System.out.println("DB #" + targetDatabase + " id the chosen for now");

    }

    public void setDataMap(Map<String, Document> m) {
        this.dataMap = m;
    }

    public void Execute() {
        System.out.println("Indexer has started from scratch");
        PorterStemmer PS = new PorterStemmer();
        long doc_id;

        int progress = 0;

        for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
            Q.insertIntoDocument(entry.getKey(), targetDatabase);
            doc_id = Q.getIdFromDocument(entry.getKey(), targetDatabase);
            ArrayList<String> res;
            /*1- Stem and insert the title*/
 /*2- Stem any thing else*/
            String[] tags = {"title", "ul", "ol", "table", "h1", "h2", "h3", "h4", "h5", "h6", "p"};//, "a" would be considered in phrase search
            ComplexInsert stmt = new ComplexInsert(targetDatabase);

            for (int i = 0; i < tags.length; i++) {
                /*Stem the above tags*/
                Elements E = entry.getValue().select(tags[i]);
                for (int k = 0; k < E.size(); k++) {
                    res = PS.StemText(E.get(k).text());
                    if (!res.isEmpty()) {
                        stmt.add(doc_id, res, tags[i] + "_" + k);
                    }
                }
            }
            stmt.Execute();
            System.out.println("document#" + (++progress) + " has been served!");
        }
        Q.deleteDB(3 - targetDatabase);

    }

    void finish() {
        Q.deleteDB(3 - targetDatabase);    
    }

}
