package frontend_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import server.Gracz;
import server.Player;
import server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;

public class MenuScreen{
    @FXML
    private TextField nickbox;
    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void renderMenu(String fxmlFile, String cssFile) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFile)).toExternalForm());

        primaryStage.setResizable(false);
        primaryStage.setTitle("TRIVIA GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        TriviaGameApp.menuScreen = loader.getController();
        TriviaGameApp.menuScreen.setPrimaryStage(primaryStage);

        TextFormatter<String> formatter = new TextFormatter<>(change ->
                change.getControlNewText().length() <= 30 ? change : null);
        TriviaGameApp.menuScreen.nickbox.setTextFormatter(formatter);
    }

    public void onHostButtonClick(ActionEvent actionEvent) {
        if(nickbox.getText().trim().isEmpty()){
            nickbox.setPromptText("WPISZ NICK!!");
        }
        else{
            try{
                //creating a server
                TriviaGameApp.setServerOnPort();

                TriviaGameApp.hostScreen = new HostScreen();
                TriviaGameApp.hostScreen.setPrimaryStage(primaryStage);
                //creating a hosting player (still a client)
                Socket playerSocket = new Socket("localhost", TriviaGameApp.server.serverSocket.getLocalPort());
                TriviaGameApp.hostPlayer = new Player(playerSocket, nickbox.getText());
                TriviaGameApp.hostScreen.renderHostScreen("HostScreen.fxml", "Styles.css");

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void onJoinButtonClick(ActionEvent actionEvent){
        if(nickbox.getText().trim().isEmpty()){
            nickbox.setPromptText("WPISZ NICK!!");
        }
        else{
            try{
                TriviaGameApp.joinScreen = new JoinScreen();
                TriviaGameApp.joinScreen.setPrimaryStage(primaryStage);
                ////////////////////////////////////////////////////////////
                //TUTAJ bylo od razu laczenmie z gra
                ///////////////////////////////////////////////////////////
                //Socket playerSocket = new Socket("localhost",5000);
                TriviaGameApp.guestPlayer = new Player(nickbox.getText());
                TriviaGameApp.joinScreen.renderJoinScreen("JoinScreen.fxml", "Styles.css");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}