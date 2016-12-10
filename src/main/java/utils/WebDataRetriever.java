package utils;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebDataRetriever {

    private static URLConnection connection = null;

    private static String[] urls = {
            "http://f.kafeteria.pl/temat/f4/przyklady-najgorszego-skapstwa-smieszne-albo-zenujace-sytuacje-ze-sknerami-p_4820467",
            "http://f.kafeteria.pl/temat/f1/hce-zalorzyc-mondry-temat-p_3197853",
            "http://f.kafeteria.pl/temat/f1/jak-namowic-meza-na-dziecko-p_3186435"
    };

    private static int[] pageNumbers = {20,4,2};


    public static List<String> getContentFromUrls(){
        List<String> content = new ArrayList<>();
        for (int index = 0; index < urls.length; index++) {
            System.out.println("Link nr " + index);
            System.out.println("pobieranie...");
            for (int i = 1; i <= pageNumbers[index]; i++) {
                content.add(getContentFromUrl(urls[index] + "/" + i));
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

    public static void saveContentToFile(String filename) {
        List<String> content = getContentFromUrls();
        content = removeHtmlTags(content);
        try {
            writeToFile(filename, content);
            System.out.println("Zapisano do pliku.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> removeHtmlTags(List<String> content) {
        List<String> result = new ArrayList<>();
        for (String sentence : content) {
            sentence = Jsoup.parse(sentence).text();
            result.add(sentence);
        }
        return result;
    }

    private static void writeToFile(String fileName, List<String> content) throws IOException {
        ContentWriter writer = new ContentWriter(fileName);
        writer.writeContentToFile(content);
        writer.close();

    }

    public static void setUrls(String[] urls) {
        WebDataRetriever.urls = urls;
    }
}
