/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSearchEngine;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr
 */
public class Crawler_Data_loader {
    queryManager qm;
    
    public Crawler_Data_loader()
    {
            qm = new queryManager();  
    }
    
   
    int get_crawled_count()
    {        int result = 0;

        ResultSet rs = qm.get_crawling_count();
        try {
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Crawler_Data_loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    Queue get_to_visit_urls() {
        
        ResultSet rs = qm.selectdUrl_from_to_visit();
        Queue<String> q = new ConcurrentLinkedQueue<String>();
        try {
            while (rs.next()) {
            q.add(rs.getString("doc_url"));
        }
        }  catch (SQLException ex) {
            Logger.getLogger(Crawler_Data_loader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return q;
    }

    Set get_visited_urls(){
     
        ResultSet rs = qm.selectdUrl_from_visited();
        Set<String> url_visited = new ConcurrentSkipListSet();

        try {
            while (rs.next()) {
                url_visited.add(rs.getString("Url"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Crawler_Data_loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url_visited;

    }

    
    
    Map<String, RobotTxtHandler> get_robot_handlers() 
    {
        Map<String, Integer> hosts = get_hosts_from_robot_handler_1();
        Map<String, RobotTxtHandler> robots = new ConcurrentHashMap();
        
        for (Map.Entry<String, Integer> entrySet : hosts.entrySet()) {
            String host = entrySet.getKey();
            Integer c_delay = entrySet.getValue();
            
            ArrayList<String> disallowed;
                disallowed = get_disallowed_urls(host);
                RobotTxtHandler H = new RobotTxtHandler(c_delay, disallowed);
                robots.put(host, H);
        }
        return robots;
    }
    
    private ArrayList get_disallowed_urls(String host){
        queryManager qmm = new queryManager();
        ResultSet rs = qmm.selectdisallowedURL_byhost(host);
        ArrayList disallowed_urls = new ArrayList();

        try {
            while (rs.next()) {
                disallowed_urls.add(rs.getString("url_disallowed"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Crawler_Data_loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disallowed_urls;
    }
    
    
    private Map get_hosts_from_robot_handler_1(){
        
        ResultSet rs = qm.selectHost_from_robot_handler_1();
        Map<String, Integer> Hosts = new ConcurrentHashMap();

        try {
            while (rs.next()) {
                Hosts.put(rs.getString("host"), rs.getInt("crawl_delay"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Crawler_Data_loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Hosts;

    }

    
}
