package utils;

import morfologik.speller.Speller;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PolishErrorDetector {
    private static final int MAX_DISTANCE = 1;
    private static PolishStemmer stemmer = new PolishStemmer();
    private static Speller speller = new Speller(stemmer.getDictionary(), MAX_DISTANCE);

    public static boolean isWordInDictionary(String word) {
        return !stemmer.lookup(word.toLowerCase()).isEmpty();
    }

    public static List<String> getWordBaseForms(String word) {
        List<String> results = new ArrayList<>();
        List<WordData> words = stemmer.lookup(word.toLowerCase());
        words.forEach(w -> results.add(w.getStem().toString()));
        return results;
    }

    public static List<String> getWordSuggestions(String word) {
        return speller.findReplacements(word);
    }

    public static boolean isNumeric(String word) {
        return Pattern.matches("\\d+", word);
    }
}
