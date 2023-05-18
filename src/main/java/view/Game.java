package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;

public class Game extends Application {
    private final int SCENE_SIZE = 600;
    private final int middle = SCENE_SIZE / 2;
    public AnchorPane anchorPane;
    private Ball central;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane gamePane = FXMLLoader.load(Game.class.getResource("game.fxml"));

        this.central = new Ball(150);
        central.setCenterX(middle);
        central.setCenterY(middle - 100);
        gamePane.getChildren().add(central);

        central.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case SPACE:
                        System.out.println("space");
                        Ball ball = new Ball(10);
                        gamePane.getChildren().add(ball);
                        ball.setCenterX(central.getCenterX());
                        ball.setCenterY(SCENE_SIZE - 10);
                        ShootingAnimation shootingAnimation = new ShootingAnimation(gamePane, central, ball);
                        shootingAnimation.play();
                        break;
                }
            }
        });

        Scene scene = new Scene(gamePane, SCENE_SIZE, SCENE_SIZE);
        stage.setScene(scene);
        central.requestFocus();
        stage.show();
    }
}
