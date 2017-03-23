/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

/**
 *
 * @author Malak Fahim
 */
public class queryManager {

    ResultSet myRes = null;       //for select query
    int res;    //for insert query
    boolean flag; //for delete query
    String sql;
    databaseManager db;

    public queryManager() {
        try {
            this.db = new databaseManager();
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ResultSet select_downloaded_pages() {
        sql = "SELECT Url, page_content FROM search_engine.downloaded_page";

        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myRes;
    }

    ResultSet selectUrlbyID(int id) {

        sql = "select doc_url from to_visit where docID =" + id;
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectIDbyUrl(String url) {
        sql = "select docID from to_visit where doc_url ='" + url + "'";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    int insertURL(int id, String url) {
        sql = "INSERT INTO to_visit(docID,doc_url)values(" + id + ",'" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }

    boolean delete_all_from_downloaded_pages() {
        sql = "delete from downloaded_page";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    boolean deleteURLbyID(int docid) {
        sql = "delete from to_visit where docID =" + docid;
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    ResultSet selectURLbyword(String woord) {
        sql = "select doc_url from to_visit,doc_words where word ='" + woord + "' AND to_visit.docID=doc_words.ID_doc";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet get_crawling_count() {
        sql = "SELECT count(ID) from downloaded_page";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ///////////////////Update database ///////////////////
    int insertURLintoVisited(String url) {
        sql = "INSERT INTO visited(Url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }

    int insertinto_host_handler(String host, String handler) {
        sql = "INSERT INTO host_robot_handler(host,robot_handler)values('" + host + "' , '" + handler + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }

    int insertinto_downloaded_page(String url, Document content) {
        sql = "INSERT INTO downloaded_page(Url,page_content)values('" + url + "' , '" + content.html().replaceAll("[^\\p{ASCII}]", "").replace("'", "\\'").replace("\"", "\\\"") + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }

    boolean deleteURLfrom_to_visit(String url) {
        sql = "delete from to_visit where doc_url ='" + url + "'";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    boolean Flush_to_visit() {
        sql = "delete from to_visit";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    boolean Flush_visited() {
        sql = "delete from visited";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    boolean Flush_robot_1() {
        sql = "delete from robot_handler_1";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    boolean Flush_robot_2() {
        sql = "delete from robot_handler_2";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    int insert_URL_into_to_visit(String url) {
        sql = "INSERT INTO to_visit(doc_url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }
    ////////////////////////////////////////////

    int insertinto_robot_handler_1(String host, int crawl_delaay) {
        sql = "INSERT INTO robot_handler_1(host,crawl_delay)values('" + host + "' , '" + crawl_delaay + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }

    int insertinto_robot_handler_2(String host, String url) {
        sql = "INSERT INTO robot_handler_2(host,url_disallowed)values('" + host + "' , '" + url + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }

    ResultSet selectdisallowedURL_byhost(String host) {
        sql = "select url_disallowed from robot_handler_2 where host ='" + host + "'";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectdUrl_from_to_visit() {
        sql = "select doc_url from to_visit";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectdUrl_from_visited() {
        sql = "select Url from visited";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectHost_from_robot_handler_1() {

        sql = "SELECT host, crawl_delay FROM search_engine.robot_handler_1";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ///////////// For Antoun /////////////////////////////////
    int insertinto_document(String url) {
        sql = "INSERT INTO document(Url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }
    ////////////////////////////////////////////

    ResultSet selectID_from_document(String url) {
        sql = "select docId from document where Url ='" + url + "'";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    int getId_from_document(String url) {
        String ID;
        int ID_integer = 0;
        ResultSet rs = this.selectID_from_document(url);
        try {
            while (rs.next()) {              //already el Resultset will be return witne only one element

                ID = (rs.getString("docId"));
                ID_integer = Integer.parseInt(ID);

            }
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID_integer;
    }
    ////////////////////////////////////////////////////

    ResultSet selectUrlbyID_from_document(int id) {
        sql = "select Url from document where docId =" + id;
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    String getUrlbyID_from_document(int id) {
        String url = "";
        ResultSet rs = this.selectUrlbyID_from_document(id);
        try {
            while (rs.next()) {
                url = (rs.getString("Url"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

    /////////////////////////////////////////////////////
    int insertinto_doc_words(long id_doc, String word, int pos, String status) {
        sql = "INSERT INTO doc_words(ID_doc,word,position,status)values(\"" + id_doc + "\" , \"" + word + "\" , \"" + pos + "\" , \"" + status + "\" );";
        res = db.insertOrUpdate(sql);
        return res;
    }

    int optimizedInsertIntoDocWords(int id_doc, ArrayList<String> word, String status) {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO doc_words(ID_doc,word,position,status)values");
        for (int i = 0; i < word.size(); i++) {
            SB.append("(\"").append(id_doc).append("\" , \"").append(word.get(i)).append("\" , \"").append(i).append("\" , \"").append(status).append("\" ),");
        }
        SB.setCharAt(SB.length()-1, ';');
        sql=SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }
    
    int optimizedInsert_into_visited(Set<String> visited_insert)
    {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO visited(Url)values");
        
        for (Iterator<String> iterator = visited_insert.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            SB.append("(\"").append(next).append("\" ),");
        }
        SB.setCharAt(SB.length()-1, ';');
        sql=SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }
    
    
    int optimizedInsert_into_to_visit(Queue<String> Q)
    {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO to_visit(doc_url)values");
        
        for (Iterator<String> iterator = Q.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            SB.append("(\"").append(next).append("\"),");
        }
        
        SB.setCharAt(SB.length()-1, ';');
        sql=SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }
    
    int optimizedInsert_into_robots(Map<String, RobotTxtHandler> robots_insert)
    {
        StringBuilder R1 = new StringBuilder();
        StringBuilder R2 = new StringBuilder();
        R1.append("INSERT INTO robot_handler_1(host, crawl_delay) values");
        R2.append("INSERT INTO robot_handler_2(host, url_disallowed) values");
        int last_size = R2.length();
        for (Map.Entry<String, RobotTxtHandler> entrySet : robots_insert.entrySet()) {
            String key = entrySet.getKey();
            RobotTxtHandler value = entrySet.getValue();
            R1.append("(\"").append(key).append("\", \"").append(value.getCrawlDelay()).append("\"),");
            
            ArrayList<String> disallowed = value.getDisallowed();
            for (Iterator<String> iterator = disallowed.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                R2.append("(\"").append(key).append("\", \"").append(next).append("\"),");
            }
        }
        R1.setCharAt(R1.length()-1, ';');
        sql=R1.toString();
        db.insertOrUpdate(sql);
        
        if(R2.length() != last_size)
        {
            R2.setCharAt(R2.length()-1, ';');
            sql = R2.toString();
            db.insertOrUpdate(sql);
        }
        return res;        
    }
    
    
    int optimizedInsert_into_Downloaded_page(Map<String, Document> pages)
    {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO downloaded_page(Url,page_content)values");
        
        for (Map.Entry<String, Document> entrySet : pages.entrySet()) {
            String key = entrySet.getKey();
            Document value = entrySet.getValue();
            SB.append("(\'").append(key).append("\',\' ").append(value.html().replaceAll("[^\\p{ASCII}]", "").replace("\'", " ").replace("\"", " ")).append("\'),");
        }
        SB.setCharAt(SB.length()-1, ';');
        sql = SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }
    
    void optimized_delete_from_visited(Set<String> visited_insert)
    {
        StringBuilder SB = new StringBuilder();
        SB.append("delete from visited where ");
        for (Iterator<String> iterator = visited_insert.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            SB.append(" Url= \'").append(next).append("\' or ");
        }
        int last_occ = SB.lastIndexOf("or"); 
        SB.replace(last_occ, last_occ+2, " ;");
        sql = SB.toString();
        try {
            db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //////////////////////////////////////////////////
    ResultSet select_DOCID_from_doc_words(String word, int pos, String status) {
        sql = "select ID_doc from doc_words where word ='" + word + "'  AND status ='" + status + "' AND position=" + pos;
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    int getId_from_doc_words(String word, int pos, String status) {
        String ID;
        int ID_integer = 0;
        ResultSet rs = this.select_DOCID_from_doc_words(word, pos, status);
        try {
            while (rs.next()) {              //already el Resultset will be return witne only one element
                ID = (rs.getString("ID_doc"));
                ID_integer = Integer.parseInt(ID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID_integer;
    }

    ResultSet selectAllIndexedDocuments() {
        sql = "SELECT document.Url FROM search_engine.document;";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    boolean delete_document(String url) {
        sql = "delete FROM search_engine.document where document.Url=\"" + url + "\";";
        try {
            return db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    ResultSet selectAllWordsByDocId(long doc_id) {
        sql = "Select word,position,status from search_engine.doc_words where doc_words.ID_doc =\"" + doc_id + "\";";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    Boolean deleteWordbyID(long Doc_id,String word, String status, String position) {
        sql = "delete FROM search_engine.doc_words where word = \""+word+"\" and position = \""+position+"\" and "
                + "status =\""+status+"\" and ID_doc = \""+Doc_id+"\";";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }
}
