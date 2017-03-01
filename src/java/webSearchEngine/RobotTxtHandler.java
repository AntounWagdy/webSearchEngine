/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSearchEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RobotTxtHandler {
	private String userAgent = "Googlebot";
	private URL base;
	private BufferedReader robotData;
	private ArrayList<URL> Disallow;
	private Integer crawlDelay = 0;
	// static private Set<URL> visited;

	/* Preparing the URL to Connect to */
	public RobotTxtHandler(URL url) {
		Disallow = new ArrayList<URL>();
		
		try {
			base = new URL(url.getProtocol() + "://" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : ""));
			String hostId = base + "/robots.txt";
			
			URL hostURL = new URL(hostId);
			URLConnection connection = hostURL.openConnection();

			robotData = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			extractData();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void extractData() {
		String inputLine;
		String[] inputLineArr = { null, null };
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
								Disallow.add(new URL(base+inputLineArr[1].trim()));
							}
						} else if (inputLineArr[0].equals("Crawl-delay:")) {
								crawlDelay = Integer.parseInt(inputLineArr[1]);
						}

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* Closing the BufferReader */
		try {
			robotData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<URL> getDisallowed() {
		return Disallow;
	}
	public Integer getCrawlDelay(){
		return crawlDelay;
	}
}
