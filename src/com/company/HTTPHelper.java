package com.company;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HTTPHelper {
    private static Logger logger = Logger.getLogger("MyLog");
    public String getTextByURL(String myUrl) throws IOException {
        String text = "";
        try {
            Document doc = Jsoup.connect(myUrl).get();
            Elements element= doc.select("div");
            text = element.text();
        } catch (IOException e) {
            e.printStackTrace();
            FileHandler fh = new FileHandler("MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("url does not exist");
            return "error";
        }
        return text;
    }
}
