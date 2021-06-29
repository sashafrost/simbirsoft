package com.company;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Logger logger = Logger.getLogger("MyLog");

    public static void main(String[] args) throws IOException {
	HTTPHelper helper = new HTTPHelper();
	Scanner sc= new Scanner(System.in);
	String url = sc.nextLine();
	if(!checkUrl(url)){
        FileHandler fh;
        fh = new FileHandler("MyLogFile.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.info(url+" not match with regex");
	    return;
    }
	String content = helper.getTextByURL(url);
	System.out.println(content);
	countWords(content.toUpperCase());
    }

    private static void countWords(String text){
        String[] arr = text.replaceAll("\\.|,|!|\\?|\"|;|:|\\[|]|\\(|\\)|\n|\t|\r|\\s", ",").split(",");
        Map<String,Integer> map =new HashMap<>();
        for(String word:arr){
            if(map.containsKey(word)&&!word.equals("")){
                int count = map.get(word)+1;
                map.put(word, count);
            }
            else map.put(word,1);
        }
        map=sortByValue(map);
        for (String key:map.keySet())
            System.out.println(key+":"+map.get(key));
    }
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    private static boolean checkUrl(String url){
        String regex = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher =pattern.matcher(url);
        return matcher.matches();
    }
}
