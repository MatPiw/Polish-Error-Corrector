package utils;

import morfologik.speller.Speller;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.util.ArrayList;
import java.util.List;

public class PolishErrorDetector {
    private static PolishStemmer stemmer = new PolishStemmer();
    private static Speller speller = new Speller(stemmer.getDictionary());

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
}
