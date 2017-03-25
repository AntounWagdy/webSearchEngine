/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
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
        this.db = databaseManager.getInstance();
    }

    ResultSet select_downloaded_pages() {
        sql = "SELECT Url, page_content FROM search_engine.downloaded_page order by ID ASC limit 150;";

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

    boolean delete_all_from_downloaded_pages() {
        sql = "delete from downloaded_page";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
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

    ////////////////////////////////////////////
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
    int insertinto_document(String url, int index) {
        sql = "INSERT INTO document" + ((index == 2) ? "2" : "") + "(Url)values('" + url + "');";
        res = db.insertOrUpdate(sql);
        return res;
    }
    ////////////////////////////////////////////

    ResultSet selectID_from_document(String url, int idx) {
        sql = "select docId" + ((idx == 2) ? "2" : "") + " from document" + ((idx == 2) ? "2" : "") + " where Url ='" + url + "'";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    int getId_from_document(String url, int idx) {
        String ID;
        int ID_integer = 0;
        ResultSet rs = this.selectID_from_document(url, idx);
        try {
            while (rs.next()) {              //already el Resultset will be return witne only one element

                ID = (rs.getString("docId" + ((idx == 2) ? "2" : "")));
                ID_integer = Integer.parseInt(ID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID_integer;
    }

    ////////////////////////////////////////////////////
    int optimizedInsert_into_visited(Set<String> visited_insert) {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO visited(Url)values");

        for (Iterator<String> iterator = visited_insert.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            SB.append("(\"").append(next).append("\" ),");
        }
        SB.setCharAt(SB.length() - 1, ';');
        sql = SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }

    int optimizedInsert_into_to_visit(Queue<String> Q) {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO to_visit(doc_url)values");

        for (Iterator<String> iterator = Q.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            SB.append("(\"").append(next).append("\"),");
        }

        SB.setCharAt(SB.length() - 1, ';');
        sql = SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }

    int optimizedInsert_into_robots(Map<String, RobotTxtHandler> robots_insert) {
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
        R1.setCharAt(R1.length() - 1, ';');
        sql = R1.toString();
        db.insertOrUpdate(sql);

        if (R2.length() != last_size) {
            R2.setCharAt(R2.length() - 1, ';');
            sql = R2.toString();
            db.insertOrUpdate(sql);
        }
        return res;
    }

    int optimizedInsert_into_Downloaded_page(Map<String, Document> pages) {
        StringBuilder SB = new StringBuilder();
        SB.append("INSERT INTO downloaded_page(Url,page_content)values");

        for (Map.Entry<String, Document> entrySet : pages.entrySet()) {
            String key = entrySet.getKey();
            Document value = entrySet.getValue();
            SB.append("(\'").append(key).append("\',\' ").append(value.html().replaceAll("[^\\p{ASCII}]", "").replace("\'", " ").replace("\"", " ")).append("\'),");
        }
        SB.setCharAt(SB.length() - 1, ';');
        sql = SB.toString();
        res = db.insertOrUpdate(sql);
        return res;
    }

    void optimized_delete_from_to_visit(Set<String> visited_insert) {
        StringBuilder SB = new StringBuilder();
        SB.append("delete from to_visit where ");
        for (Iterator<String> iterator = visited_insert.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            SB.append(" doc_url= \'").append(next).append("\' or ");
        }
        int last_occ = SB.lastIndexOf("or");
        SB.replace(last_occ, last_occ + 2, " ;");
        sql = SB.toString();
        try {
            db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //////////////////////////////////////////////////
    int getCountDocument1() {
        sql = "SELECT count(*) as total FROM search_engine.document;";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    int getCountDocument2() {
        sql = "SELECT count(*) as total FROM search_engine.document2;";
        try {
            myRes = db.select(sql);
            myRes.next();
            return myRes.getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    boolean deleteDB(int idx) {
        sql = "delete FROM search_engine.document" + ((idx == 2) ? "2" : "") + ";";

        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        sql = "delete FROM search_engine.doc_words" + ((idx == 2) ? "2" : "") + ";" + ";";

        try {
            flag &= db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    Map<String, Document> select_and_delete_pages_by_limit(int limit) {
        sql = "select * from downloaded_page limit " + limit + ";";
        Map<String, Document> m = new HashMap();
        try {
            myRes = db.select(sql);

            StringBuilder SB = new StringBuilder();
            SB.append("delete from downloaded_page where ");

            while (myRes.next()) {
                SB.append(" ID= ").append(myRes.getInt("ID")).append(" or ");
                m.put(myRes.getString("Url"), Jsoup.parse(myRes.getString("page_content")));
            }
            if (m.isEmpty()) {
                return m;
            }
            int last_occ = SB.lastIndexOf("or");
            SB.replace(last_occ, last_occ + 2, " ;");
            sql = SB.toString();
            db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
}
