package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LinkExtractService {

    //Fetch funda property links given base url, then store links and property address into a map.
    public Map<String, String> linkExtract(String url) throws IOException, InterruptedException {

        String next;
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Map<String, String> linkPropertyMap = new HashMap<>();

        while(true) {
            links.stream().filter(t -> t.text().matches("^.+56.+$"))
                    .forEach(t -> linkPropertyMap.put(t.attr("abs:href"), t.text()));
            if(links.stream().anyMatch(t -> t.text().equals("Volgende"))){
                next = links.stream().filter(t -> t.text().equals("Volgende")).findFirst().get().attr("abs:href");
            }
            else
            {
                break;
            }
            links = Jsoup.connect(next).get().select("a[href]");
        }

        return linkPropertyMap;
    }
}
