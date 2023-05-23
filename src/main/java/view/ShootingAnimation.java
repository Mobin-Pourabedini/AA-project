package view;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.Aa;
import model.Ball;
import model.Game;

import java.io.IOException;

public class ShootingAnimation extends Transition {
    private Pane pane;
    private Ball centerBall, movingBall;
    private model.Game game;
    private double degree;
    private boolean isDone = false;
    private GameController controller;

    public ShootingAnimation(GameController controller, Game game, Pane pane, Ball centerBall, Ball movingBall) {
        this.game = game;
        this.degree = game.getAngle();
        this.pane = pane;
        this.centerBall = centerBall;
        this.movingBall = movingBall;
        this.controller = controller;
        this.setCycleDuration(javafx.util.Duration.millis(1));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        if (isDone) {
            return;
        }
        int movementSpeed = 1;
        double y = movingBall.getCenterY() - Math.cos(Math.toRadians(degree)) * movementSpeed;
        double x = movingBall.getCenterX() + Math.sin(Math.toRadians(degree)) * movementSpeed;
        movingBall.setCenterY(y);
        movingBall.setCenterX(x);
        Ball fakeBall = new Ball(Aa.CENTRAL_BALL_RADIOS + 50);
        fakeBall.setCenterX(Aa.CENTRAL_BALL_X);
        fakeBall.setCenterY(Aa.CENTRAL_BALL_Y);
        for (Ball ball: game.getBalls()) {
            if (GameController.checkIntersection(movingBall, ball)) {
                this.stop();
                isDone = true;
                try {
                    controller.gameOver();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (GameController.checkIntersection(movingBall, fakeBall)) {
            game.addBall(movingBall);
            controller.addToScore();
            controller.addToProgress();
            if (game.isLastBall(movingBall)) {
                try {
                    controller.wonGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            this.stop();
            isDone = true;
            game.getAnimation().stop();
            RotatingAnimation animation = new RotatingAnimation(
                    game, pane, game.getBalls(), Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
            animation.play();
            game.setAnimation(animation);
        }
    }
}
