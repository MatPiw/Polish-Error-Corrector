package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class CorrectorView extends Application {

    private Pane root;
    private TextFlow outputPane;
    private TextArea inputText;
    private Button analyzeTextButton;


    private CorrectorPresenter presenter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
            try {
                root = FXMLLoader.load(getClass().getResource("/corrector.fxml"));
                presenter = new CorrectorPresenter();
                Scene scene = new Scene(root);

                primaryStage.setScene( scene );
                primaryStage.setResizable(false);
                primaryStage.setTitle("Polski znajdywacz błędów v 0.2");
                primaryStage.show();

                outputPane = (TextFlow) root.lookup("#outputTextFlow");
                inputText = (TextArea) root.lookup("#inputText");

                analyzeTextButton = (Button) root.lookup("#analyzeTextButton");
                analyzeTextButton.setOnMouseClicked(event -> presenter.produceOutput(inputText.getText(), outputPane));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
