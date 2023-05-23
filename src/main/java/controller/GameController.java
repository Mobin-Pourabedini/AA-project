package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.util.Duration;
import model.Aa;
import model.Ball;
import model.Game;
import model.User;
import view.*;

import java.util.List;
import java.util.Random;

public class GameController {
    private User loggedInUser;
    private Game game;
    private Pane gamePane;
    private Media knifeEffect = new Media(getClass().getResource("/media/knifeEffect.mp3").toExternalForm());
    private int remainingBalls, phaseNumber = 1;
    private final int numberOfPins, difficulty, mapIndex;
    private boolean gameOver = false, isPaused = false;
    private Ball central;
    private GameMenu gameMenu;
    private double freezePower = 0.0;

    public GameController(GameMenu gameMenu, User loggedInUser, Pane gamePane, int numberOfPins, int difficulty, int mapIndex) {
        this.loggedInUser = loggedInUser;
        this.gamePane = gamePane;
        this.remainingBalls = numberOfPins;
        this.numberOfPins = numberOfPins;
        this.difficulty = difficulty;
        this.mapIndex = mapIndex;
        this.gameMenu = gameMenu;
        this.game = createGame(2 * difficulty);
    }

    public Game createGame(int movementSpeed) {
        List<Integer> list = Aa.getGameMap(mapIndex);
        this.game = new Game(numberOfPins, movementSpeed);
        for (int pos : list) {
            Ball ball = new Ball(Aa.BALL_RADIOS);
            gamePane.getChildren().add(ball);
            moveBall(pos, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y,
                    Aa.CENTRAL_BALL_RADIOS + Aa.BALL_RADIOS + 50);
            game.getBalls().add(ball);
        }
        RotatingAnimation animation = new RotatingAnimation(
                game, gamePane, game.getBalls(), Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
        animation.play();
        game.setAnimation(animation);
        game.initBall(gamePane);
        enterPhase1();
        return game;
    }

    public Ball createCentralBall() {
        Ball ball = new Ball(Aa.CENTRAL_BALL_RADIOS);
        ball.setCenterX(Aa.CENTRAL_BALL_X);
        ball.setCenterY(Aa.CENTRAL_BALL_Y);
        GameController controller = this;
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (gameOver || isPaused) {
                    return;
                }
                switch (keyEvent.getCode()) {
                    case SPACE:
                        System.out.println("space");
                        if (game.isGameOver()) {
                            return;
                        }
                        gameMenu.setRemainingBallsText("Remaining balls: " + --remainingBalls);
                        System.out.println("space");
                        MediaPlayer mediaPlayer = new MediaPlayer(knifeEffect);
                        mediaPlayer.play();
                        ShootingAnimation shootingAnimation = new ShootingAnimation(
                                controller, game, gamePane, central, game.getCurrentBall());
                        shootingAnimation.play();
                        game.nextBall();
                        game.initBall(gamePane);
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins && !Aa.isInPhase2) {
                            enterPhase2();
                        }
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins * 2 && !Aa.isInPhase3) {
                            enterPhase3();
                        }
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins * 3 && !Aa.isInPhase4) {
                            enterPhase4();
                        }
                        break;
                    case TAB:
                        System.out.println("tab");
                        Timeline freeze = freeze(game, gamePane);
                        if (freeze == null){
                            break;
                        }
                        game.setFreezeTimeline(freeze);
                        freeze.play();
                        break;
                    case ESCAPE:
                        System.out.println("escape");
                        isPaused = true;
                        if (game.getAnimation() != null)game.getAnimation().pause();
                        if (game.getSwellTimeline() != null)game.getSwellTimeline().pause();
                        if (game.getFadeTimeline() != null)game.getFadeTimeline().pause();
                        if (game.getFreezeTimeline() != null)game.getFreezeTimeline().pause();
                        pause();
                        break;
                    case X:
                        System.out.println("x");
                        reverse(game, gamePane);
                        break;
                    case V:
                        System.out.println("v");
                        speedUp(game, gamePane);
                        break;
                    case C:
                        System.out.println("c");
                        speedDown(game, gamePane);
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
                        enterPhase1();
                        break;
                    case W:
                        System.out.println("w");
                        enterPhase2();
                        break;
                    case E:
                        System.out.println("e");
                        enterPhase3();
                        break;

                }
            }
        });
        central = ball;
        return ball;
    }

    public void reverse(Game game, Pane pane) {
        if (isPaused || gameOver) {
            return;
        }
        System.out.println("reverse");
        game.flipDirection();
        game.getAnimation().stop();
        RotatingAnimation animation = new RotatingAnimation(
                game, pane, game.getBalls(), Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
        animation.play();
        game.setAnimation(animation);
    }

    public void speedUp(Game game, Pane pane) {
        game.increaseSpeed();
        game.getAnimation().stop();
        RotatingAnimation animation = new RotatingAnimation(
                game, pane, game.getBalls(), Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
        animation.play();
        game.setAnimation(animation);
    }

    public void speedDown(Game game, Pane pane) {
        game.decreaseSpeed();
        game.getAnimation().stop();
        RotatingAnimation animation = new RotatingAnimation(
                game, pane, game.getBalls(), Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
        animation.play();
        game.setAnimation(animation);
    }

    public void enterPhase1() {
        Aa.isInPhase1 = true;
        game.setReverseTimeline(reverseTimeline(game, gamePane));
        game.getReverseTimeline().play();
    }

    public void enterPhase2() {
        Aa.isInPhase2 = true;
        phaseNumber = 2;
        gameMenu.setPhaseLabel("Phase 2");
        game.setSwellTimeline(swellTimeline(game, gamePane));
        game.getSwellTimeline().play();
    }

    public void enterPhase3() {
        Aa.isInPhase3 = true;
        phaseNumber = 3;
        gameMenu.setPhaseLabel("Phase 3");
        game.setFadeTimeline(fadeTimeline(game, gamePane));
        game.getFadeTimeline().play();
    }

    public void enterPhase4() {
        Aa.isInPhase4 = true;
        phaseNumber = 4;
        gameMenu.setPhaseLabel("Phase 4");
        game.setDegreeTimeline(degreeTimeline(game, gamePane));
        game.getDegreeTimeline().play();
    }

    private Timeline degreeTimeline(Game game, Pane pane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
            int movingStep = 0;
            int sign = 120;

            @Override
            public void handle(Event event) {
                movingStep += sign;
                double degree = (double) movingStep / 100;
                game.setAngle(degree);
                gameMenu.setDegreeLabel("degree: " + degree);
                System.out.println(degree);

                if (movingStep >= 1500) {
                    sign = -sign;
                }
                if (movingStep <= -1500) {
                    sign = -sign;
                }
            }
        }), new KeyFrame(Duration.millis(1000)));
        timeline.setCycleCount(-1);
        return timeline;
    }

    public Timeline reverseTimeline(Game game, Pane pane) {
        Random random = new Random();
        int millis = random.nextInt(4000, 6000);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(millis), event -> {
            if (!gameOver && !isPaused)
                reverse(game, pane);
        }));
        timeline.setOnFinished(event -> reverseTimeline(game, pane).play());
        return timeline;
    }

    public Timeline swellTimeline(Game game, Pane pane) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
            int movingStep = 0;
            int sign = 1;
            boolean  isDone = false;

            @Override
            public void handle(Event event) {
                if (isDone || game.isGameOver())return;
                movingStep += sign;

                for (Ball ball : game.getBalls()) {
                    ball.setRadius(Aa.BALL_RADIOS + ((double) movingStep / 1500) * Aa.BALL_RADIOS);
                }
                outer:
                for (int i = 0; i < game.getBalls().size(); i++) {
                    Ball ball = game.getBalls().get(i);
                    for (int j = i + 1; j < game.getBalls().size(); j++) {
                        Ball otherBall = game.getBalls().get(j);
                        if (checkIntersection(ball, otherBall)) {
                            gameOver();
                            isDone = true;
                            break outer;
                        }
                    }
                }

                if (movingStep == 1000) {
                    sign = -sign;
                }
                if (movingStep == 0) {
                    sign = -sign;
                }
            }
        }), new KeyFrame(Duration.millis(10)));
        timeline.setCycleCount(-1);
        return timeline;
    }

    public Timeline fadeTimeline(Game game, Pane pane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
            int movingStep = 0;
            int sign = 1;

            @Override
            public void handle(Event event) {
                movingStep += sign;

                for (Ball ball : game.getBalls()) {
                    double opacity = (double) movingStep / 100;
                    if (opacity < 0.33) {
                        opacity = 1 - opacity*3;
                    } else if (opacity > 0.66) {
                        opacity = (opacity - 0.75) * 3;
                    } else {
                        opacity = 0;
                    }
                    ball.setOpacity(opacity);
                    ball.getLine().setOpacity(opacity);
                }

                if (movingStep == 100) {
                    sign = -sign;
                }
                if (movingStep == 0) {
                    sign = -sign;
                }
            }
        }), new KeyFrame(Duration.millis(30)));
        timeline.setCycleCount(-1);
        return timeline;
    }


    public static void moveBall(int position, Ball ball, int centerX, int centerY, double ballOrbit) {
        double angleAlpha = position * ( (2 * Math.PI) / RotatingAnimation.MOVING_POINTS);
        ball.setCenterX(centerX + ballOrbit * Math.sin(angleAlpha));
        ball.setCenterY(centerY - ballOrbit * Math.cos(angleAlpha));
    }

    public static boolean checkIntersection(Ball movingBall, Ball centerBall) {
        double distance = Math.sqrt(Math.pow(movingBall.getCenterX() - centerBall.getCenterX(), 2) + Math.pow(movingBall.getCenterY() - centerBall.getCenterY(), 2));
        return distance <= movingBall.getRadius() + centerBall.getRadius();
    }

    public Timeline freeze(Game game, Pane gamePane) {
        if (gameMenu.checkFreeze()) {
            freezePower = 0;
            Paint prevPaint = central.getFill();
            return new Timeline(new KeyFrame(Duration.ZERO, event -> {
                Media media = new Media(getClass().getResource("/media/freezeEffect.mp3").toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                speedDown(game, gamePane);
                speedDown(game, gamePane);
                central.setFill(Color.BLUE);
                for (Ball ball: game.getBalls()) {
                    ball.setFill(Color.BLUE);
                    ball.getLine().setFill(Color.BLUE);
                }
            }), new KeyFrame(Duration.millis(5000), event -> {
                if (!gameOver && !isPaused) {
                    speedUp(game, gamePane);
                    speedUp(game, gamePane);
                    central.setFill(prevPaint);
                    for (Ball ball : game.getBalls()) {
                        ball.setFill(prevPaint);
                        ball.getLine().setFill(prevPaint);
                    }
                }
            }));
        } else {
            return null;
        }
    }

    public void gameOver() {
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

    public void wonGame() {
        gamePane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        System.out.println("won game");
    }

    public void addToProgress() {
        gameMenu.addToProgress();
        if (freezePower < 1) {
            freezePower += 0.1001;
        }
    }

    public void pause() {
        Popup popup = new Popup();
        popup.setHideOnEscape(false);
        VBox pane = new VBox(new Label("Paused"));
        pane.setMinWidth(300);
        pane.setMinHeight(300);
        pane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Button resume = new Button("Resume");
        resume.setOnAction(event -> {
            resume();
            popup.hide();
        });
        pane.getChildren().add(resume);
        Button mute = new Button("Mute");
        mute.setOnAction(event -> {
            Aa.muteMusic();
        });
        pane.getChildren().add(mute);
        Button unmute = new Button("Unmute");
        unmute.setOnAction(event -> {
            Aa.unmuteMusic();
        });
        pane.getChildren().add(unmute);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("music0", "music1", "music2", "music3");
        comboBox.setValue("music0");
        comboBox.setOnAction(event -> {
            Aa.setMusic(comboBox.getValue().charAt(comboBox.getValue().length() - 1) - '0');
        });
        pane.getChildren().add(comboBox);
        popup.getContent().add(pane);
        Button exit = new Button("Exit");
        exit.setOnAction(event -> {
            StartGameMenu startGameMenu = new StartGameMenu(loggedInUser);
            try {
                startGameMenu.start(LoginMenu.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            popup.hide();
        });
        pane.getChildren().add(exit);
        Button save = new Button("Save");
        save.setOnAction(event -> {
            saveGame();
        });
        pane.getChildren().add(save);
        popup.setX(450);
        popup.setY(200);
        popup.show(LoginMenu.stage);
    }

    public void resume() {
        if (game.getAnimation() != null)game.getAnimation().play();
        if (game.getSwellTimeline() != null)game.getSwellTimeline().play();
        if (game.getFadeTimeline() != null)game.getFadeTimeline().play();
        if (game.getFreezeTimeline() != null)game.getFreezeTimeline().play();
        isPaused = false;
    }

    public void saveGame() {
        Aa.saveGame(this);
    }

    public Ball getCentral() {
        return central;
    }

    public int getRemainingBalls() {
        return remainingBalls;
    }

    public double getFreezePower() {
        return freezePower;
    }

    public int getPhase() {
        return phaseNumber;
    }

    public Pane getPane() {
        return gamePane;
    }

    public void setPane(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public void resumeGame() {
        if (game.getAnimation() != null)game.getAnimation().play();
        if (game.getSwellTimeline() != null)game.getSwellTimeline().play();
        if (game.getFadeTimeline() != null)game.getFadeTimeline().play();
        if (game.getFreezeTimeline() != null)game.getFreezeTimeline().play();
        isPaused = false;
    }
}
