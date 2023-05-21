package view;

import controller.DataUtilities;
import controller.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Aa;
import model.Ball;
import model.Game;

import static controller.GameController.checkIntersection;

public class GameMenu extends Application {
    public final static int SCENE_SIZE = 600, middle = SCENE_SIZE / 2;
    private Ball central;
    private static int difficulty = 2, numberOfPins = 8, mapIndex = 2, remainingBalls;
    private static Pane gamePane;
    private static Game game;
    private static boolean gameOver = false;
    private static ProgressBar freezeBar;
    private static Label freezePower, phaseLabel, degreeLabel;

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
        remainingBalls = numberOfPins;
        Text remainingBallsText = new Text("Remaining balls: " + remainingBalls);
        remainingBallsText.setLayoutX(10);
        remainingBallsText.setLayoutY(20);
        remainingBallsText.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(remainingBallsText);
        freezeBar = new ProgressBar();
        freezeBar.setLayoutX(70);
        freezeBar.setLayoutY(Aa.SCENE_SIZE - 20);
        freezeBar.setProgress(0);
        freezeBar.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        freezeBar.setStyle("-fx-accent: gold;");
        gamePane.getChildren().add(freezeBar);
        Label freezeLabel = new Label("Freeze");
        freezeLabel.setLayoutX(30);
        freezeLabel.setLayoutY(Aa.SCENE_SIZE - 50);
        freezeLabel.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(freezeLabel);
        freezePower = new Label("0%");
        freezePower.setLayoutX(10);
        freezePower.setLayoutY(Aa.SCENE_SIZE - 25);
        freezePower.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(freezePower);
        phaseLabel = new Label("Phase 1");
        phaseLabel.setLayoutX(Aa.SCENE_SIZE - 100);
        phaseLabel.setLayoutY(20);
        phaseLabel.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(phaseLabel);
        new GameController().enterPhase1(game, gamePane);
        degreeLabel = new Label("degree: 0");
        degreeLabel.setLayoutX(Aa.SCENE_SIZE - 120);
        degreeLabel.setLayoutY(Aa.SCENE_SIZE - 50);
        degreeLabel.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(degreeLabel);

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
                        remainingBallsText.setText("Remaining balls: " + --remainingBalls);
                        System.out.println("space");
                        ShootingAnimation shootingAnimation = new ShootingAnimation(
                                game, gamePane, central, game.getCurrentBall());
                        shootingAnimation.play();
                        game.nextBall();
                        game.initBall(gamePane);
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins && !Aa.isInPhase2) {
                            new GameController().enterPhase2(game, gamePane);
                        }
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins * 2 && !Aa.isInPhase3) {
                            new GameController().enterPhase3(game, gamePane);
                        }
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins * 3 && !Aa.isInPhase4) {
                            new GameController().enterPhase4(game, gamePane);
                        }
                        break;
                    case TAB:
                        System.out.println("tab");
                        new GameController().freeze(game, gamePane);
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

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void addToProgress() {
        if (freezeBar.getProgress() >= 1) {
            return;
        }
        freezeBar.setProgress(freezeBar.getProgress() + 0.1001);
        freezePower.setText((int) (freezeBar.getProgress() * 100) + "%");
    }

    public static boolean checkFreeze() {
        if (freezeBar.getProgress() >= 1) {
            freezeBar.setProgress(0);
            freezePower.setText("0%");
            return true;
        }
        return false;
    }

    public static void setPhaseLabel(String text) {
        phaseLabel.setText(text);
    }

    public static void setDegreeLabel(String text) {
        degreeLabel.setText(text);
    }
}
