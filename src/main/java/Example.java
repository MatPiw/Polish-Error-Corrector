import utils.LevenshteinDistance;
import utils.PolishErrorDetector;
import utils.SentenceSplitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Example {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> potentialErrors = new ArrayList<>();
        /*while(true) {
            String line = scanner.next();
            System.out.println(line);
            if (!PolishErrorDetector.isWordInDictionary(line))
                System.out.println("brak słowa w słowniku");
            List<String> baseForms = PolishErrorDetector.getWordBaseForms(line);
            baseForms.forEach(w -> System.out.println(w));
        }*/

        //String sentence = "to, jest przykładowe zdanie, które przetwarzam.\nZadałeś mi pytnanie? Nie odpowiem, lel. nie ogarniam sprawy! xD... (bo ja to ogólnie taki nieogar jestem)";
        String sentence = scanner.nextLine();
        for (String w : SentenceSplitter.split(sentence)) {
            if (!PolishErrorDetector.isWordInDictionary(w))
                potentialErrors.add(w);
        }
        System.out.println(sentence);
        System.out.println("Potencjalne błędy:");
        for (String w : potentialErrors) {
            System.out.println(w);
        }

        System.out.println(LevenshteinDistance.levenshteinDistance("ktury", "który"));
    }
}
