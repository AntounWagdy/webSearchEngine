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

        sql = "select doc_url from document where docID =" + id;
        myRes = db.select(sql);
        return myRes;
    }

    ResultSet selectIDbyUrl(String url) throws SQLException {
        sql = "select docID from document where doc_url ='" + url + "'";
        myRes = db.select(sql);
        return myRes;
    }

    int insertURL(int id, String url) throws SQLException {
        sql = "INSERT INTO document(docID,doc_url)values(" + id + ",'" + url + "')";
        res = db.insertOrUpdate(sql);
        return res;
    }

    boolean deleteURLbyID(int docid) throws SQLException {
        sql = "delete from document where docID =" + docid;
        flag = db.delete(sql);
        return flag;
    }

    ResultSet selectURLbyword(String woord) throws SQLException {
        sql = "select doc_url from document,doc_words where word ='" + woord + "' AND document.docID=doc_words.ID_doc";
        myRes = db.select(sql);
        return myRes;
    }
}
