package view;

import controller.DataUtilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Aa;
import model.Game;
import model.ProfilePic;

import java.io.IOException;
import java.util.List;

public class StartGameMenu extends Application {
    private List<Integer> ballPositions;
    private static Rectangle selectGameButton;
    public static int gameIndex = 0;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = FXMLLoader.load(StartGameMenu.class.getResource("startGame.fxml"));
        HBox hBox = new HBox();
        selectGameButton = new Rectangle(200, 200);
        selectGameButton.setOnMouseClicked(event -> getGame());
        selectGameButton.setFill(new ImagePattern(Loader.getImage("/images/Untitled design (0).png")));
        pane.getChildren().add(selectGameButton);
        Rectangle rectangle = new Rectangle(200, 200);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void getGame() {
        Popup popup = new Popup();
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("choose game map"));
        HBox hBox = new HBox();
        for (int i = 0; i < 3; i++) {
            Paint paint = new ImagePattern(new Image(ProfileMenu.class.getResource(
                    "/images/Untitled design ("+i+").png").toExternalForm()));
            Rectangle rectangle = new Rectangle(200, 300, paint);
            final int index = i;
            rectangle.setOnMouseClicked(event -> {
                StartGameMenu.gameIndex = index;
                selectGameButton.setFill(new ImagePattern(Loader.getImage(
                        "/images/Untitled design ("+index+").png")));
                System.out.println("clicked" + index);
                popup.hide();
            });
            hBox.getChildren().add(rectangle);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);
        scrollPane.setPrefSize(600, 300);
        vBox.getChildren().add(scrollPane);
        popup.getContent().add(vBox);
        popup.show(LoginMenu.stage);
    }
}
