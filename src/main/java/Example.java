import model.SentenceElement;
import utils.LevenshteinDistance;
import utils.PolishErrorDetector;
import utils.SentenceSplitter;

import java.util.*;

public class Example {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //String sentence = "to, jest przykładowe zdanie, które przetwarzam.\nZadałeś mi pytnanie? Nie odpowiem, lel. nie ogarniam sprawy! xD... (bo ja to ogólnie taki nieogar jestem)";
        String sentence = "Tak jak ktos napisal napisal kocykiem nie przykrywam dziecka na gole cialo ,w lato pieluszka flanelowa a w zimie spiwor z owczej welny. kocyk służył nam do wozka na jesieni , a teraz do zabawy na dworze. nie szkoda mi go rozlozyc na trawie czy balkonie. znalesc żeka";
        //String sentence = scanner.nextLine();


        List<String> allWords = SentenceSplitter.split(sentence);
        Map<String, List<String>>potentialErrors = new HashMap<>();

        for (String w : allWords) {
            if (!PolishErrorDetector.isWordInDictionary(w)) {
                potentialErrors.put(w, PolishErrorDetector.getWordSuggestions(w));
            }
        }
        System.out.println(sentence);
        System.out.println("Potencjalne błędy:");
        for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }

        System.out.println("Poprawiony tekst: ");
        for(String w : allWords) {
            if (potentialErrors.containsKey(w)) {
                //TODO tu trzeba ogarnac jakie slowo wybrac
                System.out.print(potentialErrors.get(w).get(0) + " ");
            }
            else
                System.out.print(w + " ");
        }
    }
}
