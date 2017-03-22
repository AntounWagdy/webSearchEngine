/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Malak Fahim
 */
public class queryManager{

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

    ResultSet selectUrlbyID(int id){

        sql = "select doc_url from to_visit where docID =" + id;
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectIDbyUrl(String url){
        sql = "select docID from to_visit where doc_url ='" + url + "'";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    int insertURL(int id, String url){
        sql = "INSERT INTO to_visit(docID,doc_url)values(" + id + ",'" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
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

    ResultSet selectURLbyword(String woord){
        sql = "select doc_url from to_visit,doc_words where word ='" + woord + "' AND to_visit.docID=doc_words.ID_doc";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }
    
    ResultSet get_crawling_count()
    {
        sql = "SELECT count(ID) from downloaded_page";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ///////////////////Update database ///////////////////
    int insertURLintoVisited(String url)  {
        sql = "INSERT INTO visited(Url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }

    int insertinto_host_handler(String host, String handler) {
        sql = "INSERT INTO host_robot_handler(host,robot_handler)values('" + host + "' , '" + handler + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }

    int insertinto_downloaded_page(String url, String content){
        sql = "INSERT INTO downloaded_page(Url,page_content)values('" + url + "' , '" + content.replaceAll("[^\\p{ASCII}]", "").replace("'", "\\'").replace("\"","\\\"") + "' )";
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
    
    boolean Flush_to_visit()
    {
        sql = "delete from to_visit";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    boolean Flush_visited()
    {
        sql = "delete from visited";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }    
    
    boolean Flush_robot_1()
    {
        sql = "delete from robot_handler_1";
        try {
            flag = db.delete(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }    
    
    
    boolean Flush_robot_2()
    {
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

    int insertinto_robot_handler_1(String host, int crawl_delaay){
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

    ResultSet selectdUrl_from_to_visit(){
        sql = "select doc_url from to_visit";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectdUrl_from_visited(){
        sql = "select Url from visited";
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }

    ResultSet selectHost_from_robot_handler_1(){
        
        sql = "SELECT host, crawl_delay FROM search_engine.robot_handler_1";   
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRes;
    }
}
