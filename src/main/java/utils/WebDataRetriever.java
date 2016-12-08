package utils;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebDataRetriever {

    private static URLConnection connection = null;

    private static final String[] urls = {
            "http://f.kafeteria.pl/temat/f4/przyklady-najgorszego-skapstwa-smieszne-albo-zenujace-sytuacje-ze-sknerami-p_4820467"
    };


    public static List<String> getContentFromUrls(){
        List<String> content = new ArrayList<>();
        for (String url: urls) {
            System.out.println("pobieranie...");
            for (int i = 1; i <= 20; i++) {
                content.add(getContentFromUrl(url + "/" + i));
                System.out.println("pobrano stronę " + i);
            }

        }
        return content;
    }

    public static String getContentFromUrl(String url) {
        String content = null;
        try {
            connection = new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8");
            scanner.useDelimiter("\\Z");
            content = scanner.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }

}
