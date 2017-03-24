package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Indexer {

    Map<String, Document> dataMap;

    public Indexer(Map<String, Document> m) {
        this.dataMap = m;
    }

    public void Execute() {
        queryManager Q = new queryManager();
        PorterStemmer PS = new PorterStemmer();

        ArrayList<String> oldData = new ArrayList<>();
        ResultSet RS = Q.selectAllIndexedDocuments();
        try {
            while (RS.next()) {
                oldData.add(RS.getString("Url"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Indexer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!oldData.isEmpty()) {
            //arr is the old index , map.key is the new index
            ArrayList<String> newData = new ArrayList<>();
            for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
                newData.add(entry.getKey());
            }
            ArrayList<String> del = AlgebricSets.aDiffb(oldData, newData);
            ArrayList<String> ins = AlgebricSets.aDiffb(newData, oldData);
            System.out.println("To be deleted : " + del);
            System.out.println("To be inserted : " + ins);
            System.out.println();

            for (String url : ins) {
                Q.insertinto_document(url);
            }
            for (String url : del) {
                Q.delete_document(url);
            }
            oldData.clear();
            newData.clear();
            del.clear();
            ins.clear();
            /*here finished updating the first table*/

            long doc_id;
            for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
                doc_id = Q.getId_from_document(entry.getKey());
                ResultSet Res = Q.selectAllWordsByDocId(doc_id);
                try {
                    while (Res.next()) {
                        oldData.add(Res.getString("word") + " " + Res.getString("status") + " " + Res.getString("position"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Indexer.class.getName()).log(Level.SEVERE, null, ex);
                }
                /*extract new words from document*/
                ArrayList<String> temp = new ArrayList<>();
                String[] tags = {"title", "ul", "ol", "table", "a", "h1", "h2", "h3", "h4", "h5", "h6", "p"};
                for (int i = 0; i < tags.length; i++) {
                    /*Stem the above tags*/
                    Elements E = entry.getValue().select(tags[i]);
                    for (int k = 0; k < E.size(); k++) {
                        temp = PS.StemText(E.get(k).text());
                        for (int j = 0; j < temp.size(); j++) {
                            newData.add(temp.get(j) + " " + tags[i] + "_" + k + " " + j);
                        }
                    }
                }
                del = AlgebricSets.aDiffb(oldData, newData);
                ins = AlgebricSets.aDiffb(newData, oldData);
                System.out.println("To be deleted : " + del);
                System.out.println("To be inserted : " + ins);
                String[] dataArr;
                for (String data : del) {
                    dataArr = data.split(" ");
                    Q.deleteWordbyID(doc_id, dataArr[0], dataArr[1], dataArr[2]);//word , status , position
                }
                ComplexInsert stmt = new ComplexInsert();

                if (!ins.isEmpty()) {
                    stmt.addParsed(doc_id, ins);
                    stmt.Execute();
                }
            }
        } else {
            CreateDbFromScratch();
        }
    }

    private void CreateDbFromScratch() {
        System.out.println("Indexer has started from scratch");
        queryManager Q = new queryManager();
        PorterStemmer PS = new PorterStemmer();
        long doc_id;

        int lol = 0;

        for (Map.Entry<String, Document> entry : dataMap.entrySet()) {
            Q.insertinto_document(entry.getKey());
            doc_id = Q.getId_from_document(entry.getKey());
            ArrayList<String> res;
            /*1- Stem and insert the title*/
            /*2- Stem any thing else*/
            String[] tags = {"title", "ul", "ol", "table", "a", "h1", "h2", "h3", "h4", "h5", "h6", "p"};
            ComplexInsert stmt = new ComplexInsert();

            for (int i = 0; i < tags.length; i++) {
                /*Stem the above tags*/
                ArrayList<String> data = new ArrayList<>();
                Elements E = entry.getValue().select(tags[i]);
                for (int k = 0; k < E.size(); k++) {
                    res = PS.StemText(E.get(k).text());
                    if (!res.isEmpty()) {
                        stmt.add(doc_id, res, tags[i] + "_" + k);
                    }
                }
            }
            stmt.Execute();
            System.out.println((++lol) + " document has served");
        }
    }
}
