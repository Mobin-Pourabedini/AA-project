package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.net.URL;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("/game.fxml"));

    }
}
