/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

/**
 *
 * @author Malak Fahim
 */
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class databaseManager {

    Connection myCon = null;
    Statement myStatement = null;
    ResultSet myRes = null;       //for select query
    int res;    //for insert query
    boolean flag; //for delete query

    //try
    //{
    String userDatabase = "root";   //username and password elly katabtohom w enta bt install mysql
    String passDatabase = "root";

    public databaseManager() throws SQLException {
        myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?autoReconnect=true&useSSL=false", userDatabase, passDatabase);
        //Create a statement
        myStatement = myCon.createStatement();
    }

    ResultSet select(String sql) throws SQLException {
        myRes = myStatement.executeQuery(sql);
        return myRes;
    }

    int insertOrUpdate(String sql)  {

        
        try {
            res = myStatement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(databaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res;
    }

    boolean delete(String sql) throws SQLException {
        flag = myStatement.execute(sql);
        return flag;
    }

    void finish() throws SQLException {
        if (myRes != null) {
            myRes.close();
        }
        if (myStatement != null) {
            myStatement.close();
        }
    }

}
