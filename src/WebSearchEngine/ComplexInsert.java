package WebSearchEngine;

import java.util.ArrayList;

public class ComplexInsert {

    StringBuilder SB;
    databaseManager DB;

    ComplexInsert(int idx) {
        DB = databaseManager.getInstance();
        SB = new StringBuilder();
        SB.append("INSERT INTO doc_words").append((idx == 2) ? "2" : "").append("(ID_doc,word,position,status)values");
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
        SB.setCharAt(SB.length() - 1, ';');
        return this.DB.insertOrUpdate(SB.toString());
    }
}
