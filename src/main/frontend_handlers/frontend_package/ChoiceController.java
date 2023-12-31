package frontend_package;

import database.DataBaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class ChoiceController {

    @FXML
    private Stage primaryStage;
    @FXML
    private Label choiceText;
    public boolean IsLastQuestionRight;

    @FXML
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    public void setChoiceText() {
        if(this.IsLastQuestionRight)
            this.choiceText.setText("Wybierz kategorie pytania dla siebie:");
        else
            this.choiceText.setText("Wybierz kategorie pytania dla przeciwnika:");
    }

    public void renderChoiceScreen(String fxmlFile, String cssFile) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        ChoiceController choiceController = loader.getController();
        choiceController.setPrimaryStage(primaryStage);

        choiceController.IsLastQuestionRight = true;
        choiceController.setChoiceText();
    }

    public void ChoiceHandler(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml"));
            Parent root = loader.load();

            Scene choiceScene = new Scene(root, 1200, 800);
            choiceScene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("TRIVIA GAME");
            primaryStage.setScene(choiceScene);

            QuestionController questionController = loader.getController();
            questionController.setPrimaryStage(primaryStage);



            //test bazy daynch
            try {
                // Nawiązanie połączenia z bazą danych
                Connection connection = DataBaseHandler.connect();


                // Tworzenie obiektu Statement do wysyłania zapytań SQL
                Statement statement = connection.createStatement();

                // Przykładowe zapytanie SELECT
                String query = "SELECT * FROM Pytania ORDER BY RANDOM() LIMIT 1;";
                ResultSet resultSet = statement.executeQuery(query);

                // Przetwarzanie wyników zapytania
                while (resultSet.next()) {
                    // Pobieranie danych z kolumn
                    int pytanieID = resultSet.getInt("IDpytania");
                    String trescPytania = resultSet.getString("tresc");
                    String odpowiedzPoprawna = resultSet.getString("odpPoprawna");
                    String odpowiedz2 = resultSet.getString("odp2");
                    String odpowiedz3 = resultSet.getString("odp3");
                    String odpowiedz4 = resultSet.getString("odp4");

                    questionController.GetDataFromDB(trescPytania, odpowiedzPoprawna, odpowiedz2, odpowiedz3, odpowiedz4);

                    // Przetwarzanie danych lub wyświetlanie ich
                    //System.out.println("ID: " + pytanieID);
                    //System.out.println("Treść pytania: " + trescPytania);
                    //System.out.println("Odpowiedź poprawna: " + odpowiedzPoprawna);
                    //System.out.println("Odpowiedź 2: " + odpowiedz2);
                    ///System.out.println("Odpowiedź 3: " + odpowiedz3);
                    //System.out.println("Odpowiedź 4: " + odpowiedz4);
                }
                // Zamykanie zasobów
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            questionController.RandomizeAnswers();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
