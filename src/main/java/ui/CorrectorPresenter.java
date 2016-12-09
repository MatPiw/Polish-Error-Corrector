package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import utils.PolishErrorDetector;
import utils.SentenceSplitter;
import java.util.*;


public class CorrectorPresenter {

    private CorrectorView view;
    private Alert alert;
    private List<String> allWords;
    private Map<String, List<String>> potentialErrors;

    //used only for identifying word in text to replace (when choosing suggestions)
    private String wordToReplace;
    private String inputText;

    public CorrectorPresenter(CorrectorView view) {
        wordToReplace = "";
        inputText = "";
        potentialErrors = new HashMap<>();
        this.view = view;
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Klikłeś xD", ButtonType.OK);
    }

    public void produceOutput(String input, TextFlow outputArea) {
        inputText = input;
        outputArea.getChildren().clear();
        allWords = SentenceSplitter.split(input);
        synchronized(potentialErrors) {
            potentialErrors = lookForErrors(allWords);
        }
        /*for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }*/


        //tu się zajeżdża przy dłuższych tekstach!
        //*
        //System.out.println("Poprawiony tekst: ");
        synchronized(allWords) {
            for (String w : allWords) {
                if (potentialErrors.containsKey(w)) {
                    writeHyperText(w, outputArea);
                } else
                    writeRawText(w, outputArea);
            }
        }
        //*/
    }


    //gets old value and input text from itself (wordToReplace field in class)
    public void replaceText(String nevValue) {
        String outputText = inputText.replaceAll(wordToReplace, nevValue);
        inputText = outputText;
        writeRawText(outputText, view.getOutputTextFlow());
    }

    private void writeRawText(String text, TextFlow outputPane) {
        Label controlText = new Label(text);
        controlText.setPadding(new Insets(2));
        outputPane.getChildren().add(controlText);
    }

    private void writeHyperText(String text, TextFlow outputPane) {
        Hyperlink errorLink = new Hyperlink(text);
        errorLink.setOnMouseClicked(event -> displaySuggestions(errorLink));
        outputPane.getChildren().add(errorLink);
    }

    private void displaySuggestions(Hyperlink errorLink) {
        wordToReplace = errorLink.getText();
        ComboBox<String> suggestionsBox = view.getSuggestionsBox();
        suggestionsBox.getItems().clear();
        suggestionsBox.setDisable(false);
        suggestionsBox.getItems().addAll(potentialErrors.get(errorLink.getText()));
        /*suggestionsBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        System.out.println("Zamieniono " + errorLink.getText() + " na " + newValue);
                        errorLink.setText(newValue);
                        suggestionsBox.setDisable(true);
                    }
                });//*/
    }

    private Map<String, List<String>> lookForErrors(List<String> words) {
        Map<String, List<String>>potentialErrors = new HashMap<>();
        for (String w : words) {
            if (!PolishErrorDetector.isWordInDictionary(w)) {
                potentialErrors.put(w, PolishErrorDetector.getWordSuggestions(w));
                System.out.println("Potencjalny błąd: " + w);
            }
        }
        return potentialErrors;
    }


}
