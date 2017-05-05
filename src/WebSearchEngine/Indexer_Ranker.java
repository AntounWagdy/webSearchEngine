package WebSearchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Indexer_Ranker {

    Map<String, Document> dataMap;
    queryManager Q;
    int targetDatabase;

    public Indexer_Ranker() {
        Q = new queryManager();
    }

    void setTarget() {
        targetDatabase = (Q.getCountDocument1() == 0) ? 1 : 2;
        System.out.println("DB #" + targetDatabase + " id the chosen for now");
    }

    public void setDataMap(Map<String, Document> m) {
        this.dataMap = m;
    }

    public void Execute_Indexer() {
        PorterStemmer PS = new PorterStemmer();
        long doc_id;
        
        int progress = 0;

        for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
            Q.insertIntoDocument(entry.getKey(), targetDatabase);
            doc_id = Q.getIdFromDocument(entry.getKey(), targetDatabase);
            ArrayList<String> res;
            ArrayList<String> phrases=new ArrayList<>();

            /*1- Stem and insert the title*/
             /*2- Stem any thing else*/
            String[] tags = {"title", "ul", "ol", "table", "h1", "h2", "h3", "h4", "h5", "h6", "p"};//, "a" would be considered in phrase search
            ComplexInsert stmt = new ComplexInsert(targetDatabase);

            for (int i = 0; i < tags.length; i++) {
                /*Stem the above tags*/
                Elements E = entry.getValue().select(tags[i]);
                for (int k = 0; k < E.size(); k++) {
                    /*Works on phrases*/
                    phrases.addAll(Arrays.asList(E.get(k).text().split("\\.")));
                    /*for (int j = 0; j < splits.length; j++) {
                        Q.insert_phrase_into_phrases(doc_id, splits[j]);
                    }*/
                    
                    /*works on stemmed word by word */
                    res = PS.StemText(E.get(k).text());
                    if (!res.isEmpty()) {
                        stmt.add(doc_id, res, tags[i] + "_" + k);
                    }
                }
            }
            Q.insert_phrases_into_phrases_table(doc_id,phrases,targetDatabase);
            stmt.Execute();
            System.out.println("document#" + (++progress) + " has been served!");
        }
    }

    public void Execute_Ranker(){
        /*TODO: Implement page rank*/
        
        
    }
    void finish() {
        System.out.println("Deleting old DB#"+(3- targetDatabase));
        Q.deleteDB(3 - targetDatabase);
        System.out.println("Deleting Done");
    }

}
