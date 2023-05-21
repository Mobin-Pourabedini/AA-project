package view;

import controller.DataUtilities;
import controller.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Aa;
import model.Ball;
import model.Game;

import static controller.GameController.checkIntersection;

public class GameMenu extends Application {
    public final static int SCENE_SIZE = 600, middle = SCENE_SIZE / 2;
    private Ball central;
    private static int difficulty = 2, numberOfPins = 17, mapIndex = 0;
    private static Pane gamePane;
    private static Game game;
    private static boolean gameOver = false;

    public static void gameOver() {
        gamePane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        if (game.getFadeTimeline() != null) {
            game.getFadeTimeline().stop();
            for (Ball ball: game.getBalls()) {
                ball.setOpacity(1);
                ball.getLine().setOpacity(1);
            }
        }
        if (game.getSwellTimeline() != null) {
            game.getSwellTimeline().stop();
        }
        game.getAnimation().pause();
        gameOver = true;
        System.out.println("game over");

//        Pane losePane = new Pane();
//        Label label = new Label("You lost!");
//        label.setLayoutX(middle);
//        label.setLayoutX(middle);
//        losePane.getChildren().add(label);
//        LoginMenu.stage.setScene(new Scene(losePane, SCENE_SIZE, SCENE_SIZE));
//        LoginMenu.stage.show();
    }

    public static void wonGame() {
        gamePane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        System.out.println("won game");
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameOver = false;
        DataUtilities.fetchData();
        LoginMenu.stage = stage;
        Pane gamePane = FXMLLoader.load(GameMenu.class.getResource("game.fxml"));
        GameMenu.gamePane = gamePane;
        this.central = new Ball(Aa.CENTRAL_BALL_RADIOS);
        central.setCenterX(middle);
        central.setCenterY(middle - 100);
        gamePane.getChildren().add(central);
        game = new GameController().createGame(Aa.getGameMap(mapIndex), gamePane, numberOfPins, 2 * difficulty);
        game.initBall(gamePane);
        central.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (gameOver) {
                    return;
                }
                switch (keyEvent.getCode()) {
                    case SPACE:
                        if (game.isGameOver()) {
                            return;
                        }
                        for (int i = 0; i < game.getBalls().size(); i++) {
                            Ball ball = game.getBalls().get(i);
                            for (int j = i + 1; j < game.getBalls().size(); j++) {
                                Ball otherBall = game.getBalls().get(j);
                                if (checkIntersection(ball, otherBall)) {
                                    GameMenu.gameOver();
                                    break;
                                }
                            }
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
                    case Q:
                        System.out.println("q");
                        new GameController().enterPhase1(game, gamePane);
                        break;
                    case W:
                        System.out.println("w");
                        new GameController().enterPhase2(game, gamePane);
                        break;
                    case E:
                        System.out.println("e");
                        new GameController().enterPhase3(game, gamePane);
                        break;

                }
            }
        });

        Scene scene = new Scene(gamePane, SCENE_SIZE, SCENE_SIZE);
        stage.setScene(scene);
        central.requestFocus();
        stage.show();
    }

    public void setDifficulty(int value) {
        difficulty = value;
    }

    public void setNumberOfPins(int value) {
        numberOfPins = value;
    }

    public void setGameIndex(int gameIndex) {
        mapIndex = gameIndex;
    }
}
