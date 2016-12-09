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
    //zero or more special characters, then one or more letters/digits, then again zero or more special characters
    public static final Pattern wordPattern = Pattern.compile("([^\\p{javaAlphabetic}\\p{javaDigit}]*)([\\p{javaAlphabetic}\\p{javaDigit}]+)([^\\p{javaAlphabetic}\\p{javaDigit}]*)");
    //pattern = Pattern.compile("^([^a-zA-Z0-9¹æê³ñóœŸ¿¥ÆÊ£ÑÓŒ¯]*)([a-zA-Z0-9¹æê³ñóœŸ¿¥ÆÊ£ÑÓŒ¯]+)([^a-zA-Z0-9¹æê³ñóœŸ¿¥ÆÊ£ÑÓŒ¯]*)$");

    public SentenceElement(String prefix, String word, String suffix) {
        this.prefix = prefix;
        this.word = word;
        this.suffix = suffix;
        potentialError = false;

    }

    public boolean isPotentialError() {
        return potentialError;
    }

    public void setPotentialError(boolean potentialError) {
        this.potentialError = potentialError;
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
