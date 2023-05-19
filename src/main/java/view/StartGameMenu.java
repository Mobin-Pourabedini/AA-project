package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.ProfilePic;

public class StartGameMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = FXMLLoader.load(StartGameMenu.class.getResource("startGame.fxml"));
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Rectangle rectangle = new Rectangle(200, 200);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
