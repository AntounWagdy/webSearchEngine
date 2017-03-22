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
import org.jsoup.nodes.Document;

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

    ResultSet select_downloaded_pages()
    {
        sql = "SELECT Url, page_content FROM search_engine.downloaded_page";
        
        try {
            myRes = db.select(sql);
        } catch (SQLException ex) {
            Logger.getLogger(queryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return myRes;
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

    boolean delete_all_from_downloaded_pages()
    {
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

    int insertinto_downloaded_page(String url, Document content){
        sql = "INSERT INTO downloaded_page(Url,page_content)values('" + url + "' , '" + content.html().replaceAll("[^\\p{ASCII}]", "").replace("'", "\\'").replace("\"","\\\"") + "' )";
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
    
        ///////////// For Antoun /////////////////////////////////
    int insertinto_document(String url) throws SQLException {
        sql = "INSERT INTO document(Url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }

    void insert_url_document(String url) throws SQLException {
        this.insertinto_document(url);
    }
    ////////////////////////////////////////////

    ResultSet selectID_from_document(String url) throws SQLException {
        sql = "select docId from document where Url ='" + url + "'";
        myRes = db.select(sql);
        return myRes;
    }

    int getId_from_document(String url) throws SQLException {
        String ID;
        int ID_integer = 0;
        ResultSet rs = this.selectID_from_document(url);
        while (rs.next()) {              //already el Resultset will be return witne only one element

            ID = (rs.getString("docId"));
            ID_integer = Integer.parseInt(ID);

        }
        return ID_integer;
    }
    ////////////////////////////////////////////////////

    ResultSet selectUrlbyID_from_document(int id) throws SQLException {

        sql = "select Url from document where docId =" + id;
        myRes = db.select(sql);
        return myRes;
    }

    String getUrlbyID_from_document(int id) throws SQLException {
        String url = "";
        ResultSet rs = this.selectUrlbyID_from_document(id);
        while (rs.next()) {

            url = (rs.getString("Url"));
        }
        return url;
    }

    /////////////////////////////////////////////////////
    int insertinto_doc_words(int id_doc, String word, int pos, String status) throws SQLException {
        sql = "INSERT INTO doc_words(ID_doc,word,position,status)values('" + id_doc + "' , '" + word + "' , '" + pos + "' , '" + status + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }

    void insert_into_doc_words(int id_doc, String word, int pos, String status) throws SQLException {
        this.insertinto_doc_words(id_doc, word, pos, status);
    }

    //////////////////////////////////////////////////
    ResultSet select_DOCID_from_doc_words(String word, int pos, String status) throws SQLException {
        sql = "select ID_doc from doc_words where word ='" + word + "'  AND status ='" + status + "' AND position=" + pos;
        myRes = db.select(sql);
        return myRes;
    }

    int getId_from_doc_words(String word, int pos, String status) throws SQLException {
        String ID;
        int ID_integer = 0;
        ResultSet rs = this.select_DOCID_from_doc_words(word, pos, status);
        while (rs.next()) {              //already el Resultset will be return witne only one element

            ID = (rs.getString("ID_doc"));
            ID_integer = Integer.parseInt(ID);
        }
        return ID_integer;
    }
}
