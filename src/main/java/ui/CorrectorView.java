package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class CorrectorView extends Application {

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

    private String outputText;


    private CorrectorPresenter presenter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
            try {
                root = FXMLLoader.load(getClass().getResource("/corrector.fxml"));
                presenter = new CorrectorPresenter(this);
                Scene scene = new Scene(root);

                primaryStage.setScene( scene );
                primaryStage.setResizable(false);
                primaryStage.setTitle("Polski znajdywacz błędów v 0.2");
                primaryStage.show();

                outputTextFlow = (TextFlow) root.lookup("#outputTextFlow");
                inputText = (TextArea) root.lookup("#inputText");
                inputText.setText("Wiem rze ten tegst jesd pelen błendów ortograficznyh ale jest on tylko pżykładem dziauania programu.");
                suggestionsBox = (ComboBox<String>) root.lookup("#suggestionsBox");

                analyzeTextButton = (Button) root.lookup("#analyzeTextButton");
                analyzeTextButton.setOnMouseClicked(event -> presenter.produceOutput(inputText.getText(), outputTextFlow));

                replaceButton = (Button) root.lookup("#replaceButton");
                replaceButton.setOnMouseClicked(event -> presenter.replaceText(suggestionsBox.getValue()));

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
}
