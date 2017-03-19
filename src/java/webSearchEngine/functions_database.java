/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSearchEngine;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Malak Fahim
 */
public class functions_database {

    void insert_into_robotHandler_1(String host, int crawl_delay) throws SQLException {
        queryManager qmm = new queryManager();
        qmm.insertinto_robot_handler_1(host, crawl_delay);

    }

    void insert_into_robotHandler_2(String host, ArrayList disallowed_urls) throws SQLException {
        queryManager qmm = new queryManager();
        String url;
        for (int i = 0; i < disallowed_urls.size(); i++) {
            url = disallowed_urls.get(i).toString();
            qmm.insertinto_robot_handler_2(host, url);
        }

    }

    ArrayList get_disallowed_urls(String host) throws SQLException {
        queryManager qmm = new queryManager();
        ResultSet rs = qmm.selectdisallowedURL_byhost(host);
        ArrayList disallowed_urls = new ArrayList();

        while (rs.next()) {
            disallowed_urls.add(rs.getString("url_disallowed"));
        }
        return disallowed_urls;
    }

    Queue get_urls_from_to_visit() throws SQLException {
        queryManager qmm = new queryManager();
        ResultSet rs = qmm.selectdUrl_from_to_visit();
        Queue<URL> q = new LinkedList<URL>();
        while (rs.next()) {
            q.add(rs.getURL("doc_url"));
        }
        return q;
    }

    Set get_urls_from_visited() throws SQLException {
        queryManager qmm = new queryManager();
        ResultSet rs = qmm.selectdUrl_from_visited();
        Set<URL> url_visited = new HashSet<URL>();

        while (rs.next()) {
            url_visited.add(rs.getURL("Url"));
        }
        return url_visited;

    }

    Set get_hosts_from_robot_handler_1() throws SQLException {
        queryManager qmm = new queryManager();
        ResultSet rs = qmm.selectHost_from_robot_handler_1();
        Set<String> Hosts = new HashSet<String>();

        while (rs.next()) {
            Hosts.add(rs.getString("host"));
        }
        return Hosts;

    }

}
