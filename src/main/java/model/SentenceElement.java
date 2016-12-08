package model;

import java.util.List;

public class SentenceElement {

    private String word;
    private boolean potentialError;
    private List<String> wordSuggestions;

    public SentenceElement(String word) {
        this.word = word;
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
}
