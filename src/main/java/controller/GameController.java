package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import model.Aa;
import model.Ball;
import model.Game;
import view.GameMenu;
import view.RotatingAnimation;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameController {
    public Game createGame(List<Integer> list, Pane  pane, int totalBallCount, int movementSpeed) {
        Game game = new Game(totalBallCount, movementSpeed);
        for (int pos : list) {
            Ball ball = new Ball(Aa.BALL_RADIOS);
            pane.getChildren().add(ball);
            moveBall(pos, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y,
                    Aa.CENTRAL_BALL_RADIOS + Aa.BALL_RADIOS + 50);
            game.getBalls().add(ball);
        }
        RotatingAnimation animation = new RotatingAnimation(
                game, pane, game.getBalls(), Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
        animation.play();
        game.setAnimation(animation);
        return game;
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
        GameMenu.setPhaseLabel("Phase 2");
        game.setSwellTimeline(swellTimeline(game, pane));
        game.getSwellTimeline().play();
    }

    public void enterPhase3(Game game, Pane pane) {
        Aa.isInPhase3 = true;
        GameMenu.setPhaseLabel("Phase 3");
        game.setFadeTimeline(fadeTimeline(game, pane));
        game.getFadeTimeline().play();
    }

    public void enterPhase4(Game game, Pane pane) {
        Aa.isInPhase4 = true;
        GameMenu.setPhaseLabel("Phase 4");
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
                GameMenu.setDegreeLabel("degree: " + degree);
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
            if (!GameMenu.isGameOver())
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
                            GameMenu.gameOver();
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
        if (GameMenu.checkFreeze()) {
            Media media = new Media(getClass().getResource("/media/freezeEffect.mp3").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            speedDown(game, gamePane);
            speedDown(game, gamePane);
            Paint prevPaint = GameMenu.getCentral().getFill();
            GameMenu.getCentral().setFill(Color.BLUE);
            for (Ball ball: game.getBalls()) {
                ball.setFill(Color.BLUE);
            }
            new Timeline(new KeyFrame(Duration.millis(5000), event -> {
                if (!GameMenu.isGameOver()) {
                    speedUp(game, gamePane);
                    speedUp(game, gamePane);
                    GameMenu.getCentral().setFill(prevPaint);
                    for (Ball ball : game.getBalls()) {
                        ball.setFill(prevPaint);
                    }
                }
            })).play();
        }
    }
}
