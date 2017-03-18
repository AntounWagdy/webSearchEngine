/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSearchEngine;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public queryManager() throws SQLException {
        this.db = new databaseManager();
    }

    ResultSet selectUrlbyID(int id) throws SQLException {

        sql = "select doc_url from to_visit where docID =" + id;
        myRes = db.select(sql);
        return myRes;
    }

    ResultSet selectIDbyUrl(String url) throws SQLException {
        sql = "select docID from to_visit where doc_url ='" + url + "'";
        myRes = db.select(sql);
        return myRes;
    }

    int insertURL(int id, String url) throws SQLException {
        sql = "INSERT INTO to_visit(docID,doc_url)values(" + id + ",'" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }

    boolean deleteURLbyID(int docid) throws SQLException {
        sql = "delete from to_visit where docID =" + docid;
        flag = db.delete(sql);
        return flag;
    }

    ResultSet selectURLbyword(String woord) throws SQLException {
        sql = "select doc_url from to_visit,doc_words where word ='" + woord + "' AND to_visit.docID=doc_words.ID_doc";
        myRes = db.select(sql);
        return myRes;
    }
    ///////////////////Update database ///////////////////
    int insertURLintoVisited(String url) throws SQLException {
        sql = "INSERT INTO visited(Url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }
    
      int insertinto_host_handler(String host,String handler) throws SQLException {
        sql = "INSERT INTO host_robot_handler(host,robot_handler)values('" + host + "' , '" + handler + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }
      
       int insertinto_downloaded_page(String url,String content) throws SQLException {
        sql = "INSERT INTO downloaded_page(Url,page_content)values('" + url + "' , '" + content + "' )";
        res = db.insertOrUpdate(sql);
        return res;
    }
       
         boolean deleteURLfrom_to_visit(String url) throws SQLException {
        sql = "delete from to_visit where doc_url ='" + url + "'";
        flag = db.delete(sql);
        return flag;
    }
         
              int insert_URL_into_to_visit(String url) throws SQLException {
        sql = "INSERT INTO to_visit(doc_url)values('" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }
}
