package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import utils.PolishErrorDetector;
import utils.SentenceSplitter;
import java.util.*;


public class CorrectorPresenter {

    private Alert alert;
    private List<String> allWords;
    private  Map<String, List<String>>potentialErrors;

    public CorrectorPresenter() {
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Klikłeś xD", ButtonType.OK);
    }

    public void produceOutput(String input, TextFlow outputArea) {
        outputArea.getChildren().clear();
        allWords = SentenceSplitter.split(input);
        potentialErrors = lookForErrors(allWords);


        for (String w : potentialErrors.keySet()) {
            System.out.println(w + " - " + potentialErrors.get(w));
        }

        //*
        //System.out.println("Poprawiony tekst: ");
        for(String w : allWords) {
            if (potentialErrors.containsKey(w)) {
                writeHyperText(w, outputArea);
            }
            else
                writeRawText(w, outputArea);
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
        alert.setContentText("Klikłeś xD" + text);
        alert.show();
    }

    private Map<String, List<String>> lookForErrors(List<String> words) {
        Map<String, List<String>>potentialErrors = new HashMap<>();
        for (String w : words) {
            if (!PolishErrorDetector.isWordInDictionary(w)) {
                potentialErrors.put(w, PolishErrorDetector.getWordSuggestions(w));
            }
        }
        return potentialErrors;
    }
}
