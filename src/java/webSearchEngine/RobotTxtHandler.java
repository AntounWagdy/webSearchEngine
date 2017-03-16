/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSearchEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotTxtHandler {

    private String userAgent = "Googlebot";
    private URL base;
    private BufferedReader robotData;
    private String robot_String;  // data as string
    private ArrayList<URL> Disallow;
    private ArrayList<URL> Allow;
    private Integer crawlDelay=0;  // default
    private long last_time_stamp;

    /* Preparing the URL to Connect to */
    public RobotTxtHandler(URL url) {
        try {
            Disallow = new ArrayList<>();
            base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));
            String hostId = base + "/robots.txt";
            URL hostURL = new URL(hostId);
            URLConnection connection = hostURL.openConnection();
            robotData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            robot_String = robotData.toString();
            extractData();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public RobotTxtHandler(String s)
    {
        robotData = new BufferedReader(new StringReader(s));
        robot_String = s;
        extractData();
    }
    
    
    private void extractData() {
        String inputLine;
        String[] inputLineArr = {null, null};
        try {
            while ((inputLine = robotData.readLine()) != null) {
                if (inputLine.equals("User-agent: *") || inputLine.equals("User-agent: " + userAgent)) {
                    while ((inputLine = robotData.readLine()) != null && !inputLine.matches("User-agent:(.*)")) {
                        inputLineArr = inputLine.split(" ");
                        if (inputLineArr[0].equals("Disallow:")) {
                            if (inputLineArr[1] == null) {
                                // accept all (do nothing)
                            } else if (inputLineArr[1].equals("/")) {
                                // Refuse all
                                //TODO:what to do
                            } else {
                                Disallow.add(new URL(base + inputLineArr[1].trim()));
                            }
                        } else if (inputLineArr[0].equals("Crawl-delay:")) {
                            crawlDelay = Integer.parseInt(inputLineArr[1]);
                        } else if (inputLineArr[0].equals("Allow:")) {
                            Allow.add(new URL(base + inputLineArr[1].trim()));
                        }
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Closing the BufferReader */
        try {
            robotData.close();
        } catch (IOException ex) {
            Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<URL> getDisallowed() {
        return Disallow;
    }

    public ArrayList<URL> getAllowed() {
        return Allow;
    }

    public Integer getCrawlDelay() {
        return crawlDelay;
    }
    
    
    public synchronized void wait_for_crawl_delay(long current_time)
    {
        if(crawlDelay == 0) // when equal =0  , this means that we shouldn't care about it
            return;
        
        if(current_time - last_time_stamp >crawlDelay)
        {
            last_time_stamp = current_time;
            return;
        }
        else
        {
            try {
                Thread.sleep(crawlDelay);
            } catch (InterruptedException ex) {
                Logger.getLogger(RobotTxtHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            last_time_stamp = current_time;
        }
        
    }
    
    @Override
    public String toString()
    {
        return robot_String; 
    }
    
}

