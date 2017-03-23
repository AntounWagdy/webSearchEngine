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

    void add(int id_doc, ArrayList<String> word, String status) {
        for (int i = 0; i < word.size(); i++) {
            SB.append("(\"").append(id_doc).append("\" , \"").append(word.get(i)).append("\" , \"").append(i).append("\" , \"").append(status).append("\" ),");
        }
    }

    int Execute() {
        databaseManager DM = null;
        SB.setCharAt(SB.length() - 1, ';');
        try {
            DM = new databaseManager();
            return DM.insertOrUpdate(SB.toString());

        } catch (SQLException ex) {
            Logger.getLogger(ComplexInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
