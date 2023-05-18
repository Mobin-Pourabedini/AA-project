package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.Ball;

public class RotatingAnimation extends Transition {
    private Pane pane;
    private Ball ball;
    private int centerX, centerY;
    private int ballOrbit;
    private int movingStep = 0, MOVING_POINTS = 480;

    public RotatingAnimation(Pane pane, Ball ball, int centerX, int centerY) {
        this.pane = pane;
        this.ball = ball;
        this.centerX = centerX;
        this.centerY = centerY;
        this.setCycleDuration(javafx.util.Duration.millis(1000));
        this.ballOrbit = centerY - (int) ball.getCenterY();
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        movingStep++;

        double angleAlpha = movingStep * ( Math.PI / (MOVING_POINTS/2) );

        moveBall(ball, centerX + ballOrbit * Math.sin(angleAlpha), centerY - ballOrbit * Math.cos(angleAlpha));

        if (movingStep == MOVING_POINTS) {
            movingStep = 0;
        }
    }

    private void moveBall(Ball ball, double v, double v1) {
        ball.setCenterX(v);
        ball.setCenterY(v1);
    }
}
