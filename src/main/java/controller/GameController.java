package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import model.Aa;
import model.Ball;
import model.Game;
import model.User;
import view.GameMenu;
import view.RotatingAnimation;
import view.ShootingAnimation;

import java.util.List;
import java.util.Random;

public class GameController {
    private User loggedInUser;
    private Game game;
    private Pane gamePane;
    private Media knifeEffect = new Media(getClass().getResource("/media/knifeEffect.mp3").toExternalForm());
    private int remainingBalls;
    private final int numberOfPins, difficulty, mapIndex;
    private boolean gameOver = false;
    private Ball central;
    private GameMenu gameMenu;

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
        Game game = new Game(numberOfPins, movementSpeed);
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
        enterPhase1(game, gamePane);
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
                if (gameOver) {
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
                            enterPhase2(game, gamePane);
                        }
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins * 2 && !Aa.isInPhase3) {
                            enterPhase3(game, gamePane);
                        }
                        if ((numberOfPins - remainingBalls) * 4 >= numberOfPins * 3 && !Aa.isInPhase4) {
                            enterPhase4(game, gamePane);
                        }
                        break;
                    case TAB:
                        System.out.println("tab");
                        freeze(game, gamePane);
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
                        enterPhase1(game, gamePane);
                        break;
                    case W:
                        System.out.println("w");
                        enterPhase2(game, gamePane);
                        break;
                    case E:
                        System.out.println("e");
                        enterPhase3(game, gamePane);
                        break;

                }
            }
        });
        central = ball;
        return ball;
    }

    public void reverse(Game game, Pane pane) {
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

    public void enterPhase1(Game game, Pane pane) {
        Aa.isInPhase1 = true;
        game.setReverseTimeline(reverseTimeline(game, pane));
        game.getReverseTimeline().play();
    }

    public void enterPhase2(Game game, Pane pane) {
        Aa.isInPhase2 = true;
        gameMenu.setPhaseLabel("Phase 2");
        game.setSwellTimeline(swellTimeline(game, pane));
        game.getSwellTimeline().play();
    }

    public void enterPhase3(Game game, Pane pane) {
        Aa.isInPhase3 = true;
        gameMenu.setPhaseLabel("Phase 3");
        game.setFadeTimeline(fadeTimeline(game, pane));
        game.getFadeTimeline().play();
    }

    public void enterPhase4(Game game, Pane pane) {
        Aa.isInPhase4 = true;
        gameMenu.setPhaseLabel("Phase 4");
        game.setDegreeTimeline(degreeTimeline(game, pane));
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
            if (!gameOver)
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

    public void freeze(Game game, Pane gamePane) {
        if (gameMenu.checkFreeze()) {
            Media media = new Media(getClass().getResource("/media/freezeEffect.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            speedDown(game, gamePane);
            speedDown(game, gamePane);
            Paint prevPaint = central.getFill();
            central.setFill(Color.BLUE);
            for (Ball ball: game.getBalls()) {
                ball.setFill(Color.BLUE);
            }
            new Timeline(new KeyFrame(Duration.millis(5000), event -> {
                if (!gameOver) {
                    speedUp(game, gamePane);
                    speedUp(game, gamePane);
                    central.setFill(prevPaint);
                    for (Ball ball : game.getBalls()) {
                        ball.setFill(prevPaint);
                    }
                }
            })).play();
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
    }
}
