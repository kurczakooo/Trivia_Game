package frontend_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class HostScreen {
    public boolean CzyGraczDrugiPolaczony;
    @FXML
    private Label waiting;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setWaitingText() {
        if (this.CzyGraczDrugiPolaczony) {
            this.waiting.setText("Gracz 2 dołączył!");
            this.waiting.setTextFill(Paint.valueOf("green"));
        }
        else {
            this.waiting.setText("Oczekiwanie na gracza 2...");
        }
    }

    public void renderHostScreen(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        HostScreen hostScreen = loader.getController();
        hostScreen.setPrimaryStage(primaryStage);

        /*PauseTransition delay = new PauseTransition(Duration.seconds(1));  //symulacja czekania na 2 gracza
        delay.setOnFinished(e -> {
            playController.CzyGraczDrugiPolaczony = true;
            System.out.println(playController.CzyGraczDrugiPolaczony);
            playController.setWaitingText();
        });
        playController.CzyGraczDrugiPolaczony = false;
        System.out.println(playController.CzyGraczDrugiPolaczony);
        delay.play();
        playController.setWaitingText();*/
    }

    public void testowyhandler(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("category_choice.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Styles.css")).toExternalForm());

            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(scene);
            primaryStage.show();

            ChoiceController choiceController = loader.getController();
            choiceController.setPrimaryStage(primaryStage);

            choiceController.IsLastQuestionRight = true;
            choiceController.setChoiceText();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}