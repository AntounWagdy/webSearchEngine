package WebSearchEngine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.HttpStatusException;

public class httpRequestHandler {

    private Document Doc;

    
    boolean check_type_html(String s) {
    

        
        HttpURLConnection con = null;
        try {    
            URL url = new URL(s);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            
            //con.setConnectTimeout(10000);
            //con.setReadTimeout(10000);
            
            int code = con.getResponseCode();  // read time out, unknown host time out
            String type = con.getContentType();
          
            
            if(type == null || code != HttpURLConnection.HTTP_OK)   
                return false;
            else if (type.startsWith("text/html"))    // we can also check UTF-8
            {   
                // con.disconnect();
                //System.out.println(url.toString() + "  " + type);
                return true;
            }
        }
        catch(SocketException e)  // request sent then internet connection lost
        {
            System.out.println("Connection lost with server");
        }
        catch (UnknownHostException e)   // request can't be sent
        {
            System.out.println("Site is unreachable");
        } 
        catch (IOException ex) {
            Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        con.disconnect();
        
        return false;
    }
    
    
    boolean check_connectivity()
    {
        try {
            HttpURLConnection con = (HttpURLConnection)(new URL("https://www.google.com.eg")).openConnection();
            con.setConnectTimeout(10000);
            con.connect();
        } catch (MalformedURLException ex) {
            //Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    

    boolean valid(URL url)
    {
        try {
            URI u = url.toURI();            
        } catch (Exception e) {
            return false;
        }

        if( !(url.getProtocol()).equals("http") && !(url.getProtocol()).equals("https"))
                return false;
        
        if( url.getAuthority() == null)
            return false;
        
        
        
        return true;
    }
    
    
    boolean downloadPage(String url) {
        try {
           // System.out.println(url);
            Doc = Jsoup.connect(url).get();
        }
       /* catch (HttpStatusException e)
        {
            return false;
        }*/
        catch (IOException ex) {
           // Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }

    //Updated 
      ArrayList<URL> getUrls() {
        ArrayList<URL> myList = new ArrayList<>();
        String urll = "";
        Elements links = Doc.select("a[href]");
        
        
        for (Element link : links) {
            try {
                urll = link.absUrl("href");
                if (!urll.isEmpty()) {
                    myList.add(new URL(urll));
                    if(myList.size() == 50)
                        break;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return myList;
    }

      
    Document get_doc()
    {
        return Doc;
    }
      
      
    String getBody() {
        String s =Doc.body().text();
        String r = null;
        try {
            r = new String(s.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            return r;
        }

    
    
}