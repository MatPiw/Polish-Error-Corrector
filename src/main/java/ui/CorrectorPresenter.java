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

    public CorrectorPresenter(CorrectorView view) {
        potentialErrors = new HashMap<>();
        this.view = view;
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Klikłeś xD", ButtonType.OK);
    }

    public void produceOutput(String input, TextFlow outputArea) {
        outputArea.getChildren().clear();
        allWords = SentenceSplitter.split(input);
        synchronized(potentialErrors) {
            potentialErrors = lookForErrors(allWords);
        }
        /*for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }*/

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

    public void writeRawText(String text, TextFlow outputPane) {
        Label controlText = new Label(text);
        controlText.setPadding(new Insets(2));
        outputPane.getChildren().add(controlText);
    }

    public void writeHyperText(String text, TextFlow outputPane) {
        Hyperlink errorLink = new Hyperlink(text);
        errorLink.setOnMouseClicked(event -> displaySuggestions(errorLink.getText()));
        outputPane.getChildren().add(errorLink);
    }

    private void displaySuggestions(String text) {
        ComboBox<String> suggestionsBox = view.getSuggestionsBox();
        suggestionsBox.getItems().clear();
        suggestionsBox.setDisable(false);
        suggestionsBox.getItems().addAll(potentialErrors.get(text));
        suggestionsBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        System.out.println("Value is: "+newValue);
                    }
                });
        //suggestionsBox.getSelectionModel().select();
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
