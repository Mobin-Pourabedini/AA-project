package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.Ball;
import model.Game;

public class RotatingAnimation extends Transition {
    private Pane pane;
    private Ball ball;
    private int centerX, centerY;
    private double ballOrbit;
    private int movingStep = 0, MOVING_POINTS = 2520;
    private Game game;

    public RotatingAnimation(Game game, Pane pane, Ball ball, int centerX, int centerY) {
        this.game = game;
        this.pane = pane;
        this.ball = ball;
        this.centerX = centerX;
        this.centerY = centerY;
        this.setCycleDuration(javafx.util.Duration.millis(1000));
        this.ballOrbit = Math.sqrt(Math.pow(ball.getCenterX() - centerX, 2) + Math.pow(ball.getCenterY() - centerY, 2));
        for (int i = 1; i < MOVING_POINTS; i++) {
            double currentAlpha = movingStep * ( (2 * Math.PI) / MOVING_POINTS );
            double currentX = centerX + ballOrbit * Math.sin(currentAlpha);
            double currentY = centerY - ballOrbit * Math.cos(currentAlpha);
            double angleAlpha = i * ( (2 * Math.PI) / MOVING_POINTS );
            double x = centerX + ballOrbit * Math.sin(angleAlpha);
            double y = centerY - ballOrbit * Math.cos(angleAlpha);
            double currentDistance = Math.sqrt(Math.pow(currentX - ball.getCenterX(), 2) + Math.pow(currentY - ball.getCenterY(), 2));
            double distance = Math.sqrt(Math.pow(x - ball.getCenterX(), 2) + Math.pow(y - ball.getCenterY(), 2));
            if (currentDistance > distance) {
                movingStep = i;
            }
        }
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        int movementSpeed = game.getMovementSpeed();
        movingStep += game.getDirection() * movementSpeed;
        movingStep = (movingStep + MOVING_POINTS) % MOVING_POINTS;

        double angleAlpha = movingStep * ( (2 * Math.PI) / MOVING_POINTS );

        moveBall(ball, centerX + ballOrbit * Math.sin(angleAlpha), centerY - ballOrbit * Math.cos(angleAlpha));

    }

    private void moveBall(Ball ball, double v, double v1) {
        ball.setCenterX(v);
        ball.setCenterY(v1);
    }
}
