package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.WebDataRetriever;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CorrectorView extends Application {

    private Stage primaryStage;

    private Pane root;
    @FXML
    private TextFlow outputTextFlow;
    @FXML
    private TextArea inputText;
    @FXML
    private Button analyzeTextButton;
    @FXML
    private ComboBox<String> suggestionsBox;
    @FXML
    private Button replaceButton;
    @FXML
    private Button replaceAllButton;
    @FXML
    private Button dontReplaceButton;
    @FXML
    private Button exportButton;

    private String outputText;

    private CorrectorPresenter presenter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
            try {
                this.primaryStage = primaryStage;
                root = FXMLLoader.load(getClass().getResource("/corrector.fxml"));
                presenter = new CorrectorPresenter(this);
                Scene scene = new Scene(root);

                primaryStage.setScene( scene );
                primaryStage.setResizable(false);
                primaryStage.setTitle("Polski znajdywacz błędów v 0.2");
                primaryStage.show();

                //WebDataRetriever.saveContentToFile("content.txt");
                outputTextFlow = (TextFlow) root.lookup("#outputTextFlow");
                inputText = (TextArea) root.lookup("#inputText");
                inputText.setText("Wiem, rze ten tegst jesd pelen błendów ortograficznyh! ale jest on tylko pżykładem dziauania programu.");
                suggestionsBox = (ComboBox<String>) root.lookup("#suggestionsBox");

                analyzeTextButton = (Button) root.lookup("#analyzeTextButton");
                analyzeTextButton.setOnMouseClicked(event -> presenter.produceOutput(inputText.getText(), outputTextFlow));

                replaceButton = (Button) root.lookup("#replaceButton");
                replaceButton.setOnMouseClicked(event -> presenter.replaceText(suggestionsBox.getValue()));

                replaceAllButton = (Button) root.lookup("#replaceAllButton");
                replaceAllButton.setOnMouseClicked(event -> presenter.replaceAll(suggestionsBox.getValue()));

                dontReplaceButton = (Button) root.lookup("#dontReplaceButton");
                dontReplaceButton.setOnMouseClicked(event -> presenter.ignore());

                exportButton = (Button) root.lookup("#exportButton");
                exportButton.setOnMouseClicked(event -> export());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public ComboBox<String> getSuggestionsBox() {
        return suggestionsBox;
    }

    public TextFlow getOutputTextFlow() {
        return outputTextFlow;
    }

    public Button getReplaceButton() {
        return replaceButton;
    }

    public Button getReplaceAllButton() {
        return replaceAllButton;
    }

    public Button getDontReplaceButton() {
        return dontReplaceButton;
    }

    private void export() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz tekst wyjściowy");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        //System.out.println(pic.getId());
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                presenter.export(file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Plik zapisany pomyślnie.", ButtonType.OK);
                alert.setTitle("Informacja");
                alert.setHeaderText("");
                alert.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
