package examples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class httpRequestHandler {

    private Document Doc;

    boolean check_type_html(URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            String type = con.getContentType();
            con.disconnect();
            if (type.startsWith("text/html")) {
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    void downloadPage(URL url) {
        try {
            Doc = Jsoup.connect(url.toString()).get();
        } catch (IOException ex) {
            Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<URL> getUrls() {
        ArrayList<URL> myList = new ArrayList<>();
        String urll = "";
        Elements links = Doc.select("a[href]");
        
        
        for (Element link : links) {
            try {
                urll = link.absUrl("href");
                if (!urll.isEmpty()) {
                    myList.add(new URL(urll));
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(httpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return myList;
    }

    String getBody() {
        return Doc.body().text();
    }

}
