package model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceElement {


    private String word;

    //special characters before and after the word
    private String prefix;
    private String suffix;

    private boolean potentialError;
    private List<String> wordSuggestions;
    private final Pattern pattern;

    public SentenceElement(String word) {
        //zero or more special characters, then one or more letters/digits, then again zero or more special characters
        pattern = Pattern.compile("([^\\p{LD}]*)([\\p{LD}]+)([^\\p{LD}]*)");
        Matcher matcher = pattern.matcher(word);
        matcher.matches();
        try {
            this.prefix = matcher.group(1);
            this.word = matcher.group(2);
            this.suffix = matcher.group(3);
        } catch (IllegalStateException e) {
            System.out.println(word + " ???");
        }
        potentialError = false;

    }

    public boolean isPotentialError() {
        return potentialError;
    }

    public void setPotentialError(boolean potentialError) {
        this.potentialError = potentialError;
    }

    public List<String> getWordSuggestions() {
        return wordSuggestions;
    }

    public void setWordSuggestions(List<String> wordSuggestions) {
        this.wordSuggestions = wordSuggestions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return prefix + word + suffix;
    }
}
