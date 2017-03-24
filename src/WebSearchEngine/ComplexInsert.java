package WebSearchEngine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComplexInsert {

    StringBuilder SB;

    ComplexInsert() {
        SB = new StringBuilder();
        SB.append("INSERT INTO doc_words(ID_doc,word,position,status)values");
    }

    void add(long id_doc, ArrayList<String> word, String status) {
        for (int i = 0; i < word.size(); i++) {
            SB.append("(\"").append(id_doc).append("\" , \"").append(word.get(i)).append("\" , \"").append(i).append("\" , \"").append(status).append("\" ),");
        }
    }

    void addParsed(long id_doc, ArrayList<String> dataArr) {
        String[] parsed;
        for (String data : dataArr) {
            parsed = data.split(" ");
            SB.append("(\"").append(id_doc).append("\" , \"").append(parsed[0]).append("\" , \"").append(parsed[2]).append("\" , \"").append(parsed[1]).append("\" ),");
        }
    }

    int Execute() {
        databaseManager DM;
        SB.setCharAt(SB.length() - 1, ';');
        
        System.out.println(SB.toString());
        try {
            DM = new databaseManager();
            return DM.insertOrUpdate(SB.toString());

        } catch (SQLException ex) {
            Logger.getLogger(ComplexInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
