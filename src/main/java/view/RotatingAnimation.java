package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Aa;
import model.Ball;
import model.Game;

import java.util.ArrayList;
import java.util.List;

public class RotatingAnimation extends Transition {
    private Pane pane;
    private List<Ball> balls;
    private int centerX, centerY;
    private double ballOrbit;
    private List<Integer> initMovingSteps = new ArrayList<>();
    public static int MOVING_POINTS = 2520;
    private Game game;
    private int direction;
    public RotatingAnimation(Game game, Pane pane, List<Ball> balls, int centerX, int centerY) {
        this.setInterpolator(javafx.animation.Interpolator.LINEAR);
        this.game = game;
        this.pane = pane;
        this.balls = balls;
        this.centerX = centerX;
        this.centerY = centerY;
        this.direction = game.getDirection();
        for (Ball ball: balls) {
            if (ball.getLine() == null) {
                Line line = new Line(ball.getCenterX(), ball.getCenterY(), centerX, centerY);
                ball.setLine(line);
                pane.getChildren().add(line);
            } else {
                ball.getLine().setStartX(ball.getCenterX());
                ball.getLine().setStartY(ball.getCenterY());
                ball.getLine().setEndX(centerX);
                ball.getLine().setEndY(centerY);
            }
        }
        this.setCycleDuration(javafx.util.Duration.millis(10));
        this.ballOrbit = Aa.BALL_RADIOS + Aa.CENTRAL_BALL_RADIOS + 50;
        for (Ball ball: balls) {
            int initMovingStep = getInitMovingStep(centerX, centerY, ball);
            initMovingSteps.add(initMovingStep);
        }
        this.setCycleCount(-1);
    }

    private int getInitMovingStep(int centerX, int centerY, Ball ball) {
        int initMovingStep = 0;
        for (int i = 1; i < MOVING_POINTS; i++) {
            double currentAlpha = initMovingStep * ( (2 * Math.PI) / MOVING_POINTS );
            double currentX = centerX + ballOrbit * Math.sin(currentAlpha);
            double currentY = centerY - ballOrbit * Math.cos(currentAlpha);
            double angleAlpha = i * ( (2 * Math.PI) / MOVING_POINTS );
            double x = centerX + ballOrbit * Math.sin(angleAlpha);
            double y = centerY - ballOrbit * Math.cos(angleAlpha);
            double currentDistance = Math.sqrt(Math.pow(currentX - ball.getCenterX(), 2) + Math.pow(currentY - ball.getCenterY(), 2));
            double distance = Math.sqrt(Math.pow(x - ball.getCenterX(), 2) + Math.pow(y - ball.getCenterY(), 2));
            if (currentDistance > distance) {
                initMovingStep = i;
            }
        }
        return initMovingStep;
    }

    @Override
    protected void interpolate(double v) {
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            int initMovingStep = initMovingSteps.get(i);
            Line line = ball.getLine();
            initMovingStep += game.getMovementSpeed() * direction;
            initMovingStep = (initMovingStep + MOVING_POINTS) % MOVING_POINTS;
            initMovingSteps.set(i, initMovingStep);

            double angleAlpha = initMovingStep * ((2 * Math.PI) / MOVING_POINTS);

            moveBall(ball, centerX + ballOrbit * Math.sin(angleAlpha), centerY - ballOrbit * Math.cos(angleAlpha));
            line.setStartX(ball.getCenterX());
            line.setStartY(ball.getCenterY());
        }
    }

    private void moveBall(Ball ball, double v, double v1) {
        ball.setCenterX(v);
        ball.setCenterY(v1);
    }
}
