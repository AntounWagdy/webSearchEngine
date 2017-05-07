package WebSearchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Indexer_Ranker {

    Map<String, Document> dataMap;
    queryManager Q;
    int targetDatabase;
    
    Map<String, ArrayList<String>> pages;
    Map<String, Integer> link_counter;
    Map<String, Double> pg_rank;    
    ArrayList<String> temp_arrList;
    private final double default_rank;
    private double rank_mainPage;
    private final int max_numPages;
    private final double damping_parameter = 0.7;
    private final int MAX_ITERATIONS = 20000;

    
    
    public Indexer_Ranker(Map<String, ArrayList<String>> InLinks, Map<String, Integer> OutDegree,int _max) {
        max_numPages = _max;
        Q = new queryManager();
        pages = InLinks;
        pg_rank = new HashMap<>();
        link_counter = OutDegree;
        temp_arrList = new ArrayList<>();
        default_rank = (1.0 - damping_parameter) * (1.0 / max_numPages);
        
        for (Map.Entry<String, ArrayList<String>> entry : pages.entrySet()) {
            pg_rank.put(entry.getKey(), default_rank);
            temp_arrList = entry.getValue();
            for (int i = 0; i < temp_arrList.size(); i++) {
                pg_rank.put(temp_arrList.get(i), default_rank);
            }
        }
        
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
            ArrayList<String> phrases = new ArrayList<>();

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
            Q.insert_phrases_into_phrases_table(doc_id, phrases, targetDatabase);
            stmt.Execute();
            System.out.println("document#" + (++progress) + " has been served!");
        }
    }

    
    
    
    
    public void Execute_Ranker() {
        
        for (int index = 0; index < MAX_ITERATIONS; index++) {
            for (Map.Entry<String, ArrayList<String>> entry : pages.entrySet()) {
                pg_rank.put(entry.getKey(), default_rank);
                temp_arrList = entry.getValue();
                rank_mainPage = pg_rank.get(entry.getKey());
                for (int i = 0; i < temp_arrList.size(); i++) {
                    rank_mainPage += damping_parameter * (pg_rank.get(temp_arrList.get(i)) / link_counter.get(temp_arrList.get(i)));
                }
                pg_rank.put(entry.getKey(), rank_mainPage);
            }
        }
        
        /*saving data to database*/
        long id;
        for (Map.Entry<String, Double> entry : pg_rank.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            id = Q.getIdFromDocument(entry.getKey(), targetDatabase);
            Q.insertIntoRank(id,entry.getValue(),targetDatabase);
        }

    }

    void finish() {
        System.out.println("Deleting old DB#" + (3 - targetDatabase));
        Q.updateDatabase(targetDatabase);
        Q.deleteDB(3 - targetDatabase);
        System.out.println("Deleting Done");
    }

}
