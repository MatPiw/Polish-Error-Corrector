package ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import model.SentenceElement;
import utils.PolishErrorDetector;
import utils.SentenceSplitter;
import java.util.*;


public class CorrectorPresenter {

    private CorrectorView view;
    private List<String> splittedSentence;
    private List<SentenceElement> allWords;
    private Map<String, List<String>> potentialErrors;

    private List<Labeled> outputControls;

    //used only for identifying word in text to replace (when choosing suggestions)
    private int wordToReplaceIndex;
    private String wordToReplace;

    private String inputText;

    public CorrectorPresenter(CorrectorView view) {
        wordToReplace = "";
        inputText = "";
        potentialErrors = new HashMap<>();
        outputControls = new ArrayList<>();
        this.view = view;
    }

    public void produceOutput(String input, TextFlow outputArea) {
        System.out.println("Rozpoczęto wyszukiwanie błędów...");
        allWords = new ArrayList<>();
        outputControls = new ArrayList<>();
        inputText = input;
        outputArea.getChildren().clear();
        splittedSentence = SentenceSplitter.split(input);
        splittedSentence.forEach(w -> allWords.add(new SentenceElement(w)));

        Thread errorLooker = new Thread() {
            @Override
            public void run() {
                potentialErrors = lookForErrors(splittedSentence);
            }
        };
        errorLooker.start();
        try {
            errorLooker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zakończono wyszukiwanie błędów. Znalezionych błędów: " + potentialErrors.size());
        /*for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }*/
        //tu się zajeżdża przy dłuższych tekstach!

        if (outputControls.size() < 20)
            displayOutput(outputArea);
        else
            displayOutput(outputArea, 0, 20);

    }

    private Map<String, List<String>> lookForErrors(List<String> words) {
        Map<String, List<String>>potentialErrors = new HashMap<>();
        words.forEach(w -> {
            //String w = element.getWord();
            if (!PolishErrorDetector.isWordInDictionary(w)) {
                potentialErrors.put(w, PolishErrorDetector.getWordSuggestions(w));
                //element.setPotentialError(true);
                //element.setWordSuggestions(PolishErrorDetector.getWordSuggestions(w));
                System.out.print("Potencjalny błąd: " + w);
                System.out.println(", sugestie: " + potentialErrors.get(w).size());
            }
        });
        return potentialErrors;
    }

    private void displaySuggestions(Hyperlink errorLink) {
        wordToReplace = errorLink.getText();
        wordToReplaceIndex = outputControls.indexOf(errorLink);
        ComboBox<String> suggestionsBox = view.getSuggestionsBox();
        suggestionsBox.getItems().clear();
        suggestionsBox.setDisable(false);
        view.getReplaceButton().setDisable(false);
        view.getReplaceAllButton().setDisable(false);
        view.getDontReplaceButton().setDisable(false);
        suggestionsBox.getItems().addAll(potentialErrors.get(errorLink.getText()));

    }

    //gets old value and input text from itself (wordToReplace field in class)
    public void replaceText(String nevValue) {
        if (nevValue != null) {
            addWordSuggestion(nevValue);
            outputControls.remove(wordToReplaceIndex);
            makeRawTextControl(nevValue, wordToReplaceIndex, Color.BLUE);
            updateOutput();
            view.getSuggestionsBox().setDisable(true);
            view.getReplaceButton().setDisable(true);
            view.getReplaceAllButton().setDisable(true);
            view.getDontReplaceButton().setDisable(true);
        }
    }

    public void replaceAll(String nevValue) {
        List<Integer> indexes = new ArrayList<>();
        if (nevValue != null) {
            addWordSuggestion(nevValue);
            for (Labeled h : outputControls) {
                if (h instanceof Hyperlink && h.getText().equals(wordToReplace) ) {
                        indexes.add(outputControls.indexOf(h));
                }
            }

            for (int ind : indexes) {
                outputControls.remove(ind);
                makeRawTextControl(nevValue, ind, Color.BLUE);
            }
            updateOutput();
            view.getSuggestionsBox().setDisable(true);
            view.getReplaceButton().setDisable(true);
            view.getReplaceAllButton().setDisable(true);
            view.getDontReplaceButton().setDisable(true);
        }
    }

    private void addWordSuggestion(String nevValue) {
        if (!potentialErrors.get(wordToReplace).contains(nevValue))
            potentialErrors.get(wordToReplace).add(nevValue);
    }

    public void ignore() {
        replaceAll(wordToReplace);
    }

    private void displayOutput(TextFlow outputArea) {
        generateControlsForOutput();
        updateOutput();
    }

    private void displayOutput(TextFlow outputArea, int beginIndex, int endIndex) {
        generateControlsForOutput();
        outputArea.getChildren().clear();
        outputArea.getChildren().addAll(outputControls.subList(beginIndex, endIndex));
    }

    private void updateOutput() {
        view.getOutputTextFlow().getChildren().clear();
        view.getOutputTextFlow().getChildren().addAll(outputControls);
    }


    private void generateControlsForOutput() {
        System.out.println("Rozpoczęto tworzenie kontrolek...");
        for (String w : splittedSentence) {
            if (potentialErrors.containsKey(w)) {
                if (potentialErrors.get(w).size() != 1)
                    makeHyperlink(w);
                else
                    makeRawTextControl(potentialErrors.get(w).get(0), Color.GREEN);
            } else
                makeRawTextControl(w, Color.BLACK);
        }
        System.out.println("Zakończono tworzenie kontrolek.");
    }


//generate Text or Hyperlink control
    private void makeRawTextControl(String text, Color color) {
        //Text controlText = new Text(text);
        Label controlText = new Label(text);
        controlText.setPadding(new Insets(1));
        controlText.setTextFill(color);
        outputControls.add(controlText);
        //outputPane.getChildren().add(controlText);
        System.out.println("Stworzono kontrolkę Text o wartości " + text);
    }

    private void makeRawTextControl(String text, int index, Color color) {
        //Text controlText = new Text(text);
        Label controlText = new Label(text);
        controlText.setPadding(new Insets(1));
        controlText.setTextFill(color);
        outputControls.add(index, controlText);
        //outputPane.getChildren().add(controlText);
        System.out.println("Stworzono kontrolkę Text o wartości " + text);
    }


    private void makeHyperlink(String text) {
        Hyperlink errorLink = new Hyperlink(text);
        errorLink.setOnMouseClicked(event -> displaySuggestions(errorLink));
        errorLink.setTextFill(Color.RED);
        outputControls.add(errorLink);
        //outputPane.getChildren().add(errorLink);
        System.out.println("Stworzono kontrolkę Hyperlink o wartości " + text);
    }
}
