import model.SentenceElement;
import org.jsoup.Jsoup;
import utils.*;

import java.io.IOException;
import java.util.*;

public class Example {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sentence = "to, jest! przykładowe? zdanie, (które) przetwarzam.\nZadałeś mi pytnanie? Nie odpowiem, lel. nie ogarniam sprawy! xD... (bo ja to ogólnie taki nieogar jestem)";

        //String sentence = scanner.nextLine();
        List<SentenceElement> allWords = new ArrayList<>();
        List<String> splittedSentence = SentenceSplitter.split(sentence, " ");
        splittedSentence.forEach(w -> {
            SentenceElement element = new SentenceElement(w);
            allWords.add(element);
            System.out.println(element);
        });



        //uncomment this to get content from webpages and save to file
        //WebDataRetriever.saveContentToFile("content.txt");


        //rozszerzyc dla wszystkich elem. z listy
        /*List<String> allWords = SentenceSplitter.split(sentence,);
        Map<String, List<String>>potentialErrors = new HashMap<>();

        for (String w : allWords) {
            if (!PolishErrorDetector.isWordInDictionary(w)) {
                potentialErrors.put(w, PolishErrorDetector.getWordSuggestions(w));
            }
        }

        System.out.println("Potencjalne błędy:");
        for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }*/

    }

}
