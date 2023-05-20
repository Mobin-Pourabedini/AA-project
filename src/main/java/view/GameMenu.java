package view;

import controller.DataUtilities;
import controller.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.Aa;
import model.Ball;
import model.Game;

import java.util.HashMap;
import java.util.Map;

public class GameMenu extends Application {
    public final static int SCENE_SIZE = 600;
    public final static int middle = SCENE_SIZE / 2;
    public AnchorPane anchorPane;
    private Ball central;

    public static void endGame() {
        System.out.println("end game");
        Pane losePane = new Pane();
        Label label = new Label("You lost!");
        label.setLayoutX(middle);
        label.setLayoutX(middle);
        losePane.getChildren().add(label);
        LoginMenu.stage.setScene(new Scene(losePane, SCENE_SIZE, SCENE_SIZE));
        LoginMenu.stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        DataUtilities.fetchData();
        LoginMenu.stage = stage;
        Pane gamePane = FXMLLoader.load(GameMenu.class.getResource("game.fxml"));

        this.central = new Ball(Aa.CENTRAL_BALL_RADIOS);
        central.setCenterX(middle);
        central.setCenterY(middle - 100);
        gamePane.getChildren().add(central);
        Game game = new GameController().createGame(Aa.getGameMap(2), gamePane, 5, 5);
        game.initBall(gamePane);
        central.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case SPACE:
                        if (game.isGameOver()) {
                            return;
                        }
                        System.out.println("space");
                        ShootingAnimation shootingAnimation = new ShootingAnimation(
                                game, gamePane, central, game.getCurrentBall());
                        shootingAnimation.play();
                        game.nextBall();
                        game.initBall(gamePane);
                        break;
                    case X:
                        System.out.println("x");
                        new GameController().reverse(game, gamePane);
                        break;
                    case V:
                        System.out.println("v");
                        new GameController().speedUp(game, gamePane);
                        break;
                    case C:
                        System.out.println("c");
                        new GameController().speedDown(game, gamePane);
                        break;
                    case A:
                        System.out.println("a");
                        game.setAngle(game.getAngle() - 5);
                        break;
                    case D:
                        System.out.println("d");
                        game.setAngle(game.getAngle() + 5);
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
