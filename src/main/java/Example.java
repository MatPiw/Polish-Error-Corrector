import model.SentenceElement;
import org.jsoup.Jsoup;
import utils.*;

import java.io.IOException;
import java.util.*;

public class Example {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //String sentence = "to, jest przykładowe zdanie, które przetwarzam.\nZadałeś mi pytnanie? Nie odpowiem, lel. nie ogarniam sprawy! xD... (bo ja to ogólnie taki nieogar jestem)";
        //String sentence = "Tak jak ktos napisal napisal kocykiem nie przykrywam dziecka na gole cialo ,w lato pieluszka flanelowa a w zimie spiwor z owczej welny. kocyk służył nam do wozka na jesieni , a teraz do zabawy na dworze. nie szkoda mi go rozlozyc na trawie czy balkonie. znalesc żeka";
        String sentence = scanner.nextLine();

        /*odznaczyc jak chcesz pobrac content ze stronek
        List<String> content = getContent();
        content = removeHtmlTags(content);
        try {
            writeToFile("content.txt", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //*/

        //rozszerzyc dla wszystkich elem. z listy
        List<String> allWords = SentenceSplitter.split(sentence);
        Map<String, List<String>>potentialErrors = new HashMap<>();

        for (String w : allWords) {
            if (!PolishErrorDetector.isWordInDictionary(w)) {
                potentialErrors.put(w, PolishErrorDetector.getWordSuggestions(w));
            }
        }
        //System.out.println(sentence);
        //*
        System.out.println("Potencjalne błędy:");
        for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }

        /*
        System.out.println("Poprawiony tekst: ");
        for(String w : allWords) {
            if (potentialErrors.containsKey(w)) {
                //TODO tu trzeba ogarnac jakie slowo wybrac
                System.out.print(potentialErrors.get(w).get(0) + " ");
            }
            else
                System.out.print(w + " ");
        }
        //*/
    }

    private static void writeToFile(String fileName, List<String> content) throws IOException {
        ContentWriter writer = new ContentWriter(fileName);
        writer.writeContentToFile(content);
        writer.close();

    }

    private static List<String> getContent() {
        List<String> content = WebDataRetriever.getContentFromUrls();
        return content;
    }

    private static List<String> removeHtmlTags(List<String> content) {
        List<String>result = new ArrayList<>();
        for (String sentence : content) {
            sentence = Jsoup.parse(sentence).text();
            result.add(sentence);
        }
        return result;
    }
}
