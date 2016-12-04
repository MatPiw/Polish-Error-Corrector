package utils;

import morfologik.stemming.PolishStemmer;
import morfologik.stemming.WordData;
import java.util.ArrayList;
import java.util.List;

public class PolishErrorDetector {
    private static PolishStemmer stemmer = new PolishStemmer();

    public static boolean isWordInDictionary(String word) {
        return !stemmer.lookup(word.toLowerCase()).isEmpty();
    }

    public static List<String> getWordBaseForms(String word) {
        List<String> results = new ArrayList<>();
        List<WordData> words = stemmer.lookup(word.toLowerCase());
        words.forEach(w -> results.add(w.getStem().toString()));
        return results;
    }
}
