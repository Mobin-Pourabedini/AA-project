package model;

import javafx.scene.layout.Pane;
import view.GameMenu;

import java.util.List;
import java.util.Map;

public class Game {

    private int totalBallCount;
    private int currentBallCount;
    private int direction = 1, movementSpeed;
    private double angle = 0;
    List<Ball> balls = new java.util.ArrayList<>();
    private Ball[] ballsArray;

    public Game(int totalBallCount, int movementSpeed) {
        this.movementSpeed = movementSpeed;
        this.totalBallCount = totalBallCount;
        ballsArray = new Ball[totalBallCount];
        for (int i = 0; i < totalBallCount; i++) {
            ballsArray[i] = new Ball(10);
        }
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public int getDirection() {
        return direction;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public double getAngle() {
        return angle;
    }

    public boolean isGameOver() {
        return currentBallCount == totalBallCount;
    }

    public void initBall(Pane pane) {
        if (currentBallCount == totalBallCount) {
            return;
        }
        ballsArray[currentBallCount].setCenterY(GameMenu.SCENE_SIZE - 10);
        ballsArray[currentBallCount].setCenterX(GameMenu.middle);
        pane.getChildren().add(ballsArray[currentBallCount]);
    }

    public Ball getCurrentBall() {
        return ballsArray[currentBallCount];
    }

    public void nextBall() {
        currentBallCount++;
    }

    public void flipDirection() {
        direction *= -1;
    }

    public void increaseSpeed() {
        if (movementSpeed < 10) {
            movementSpeed += 1;
        }
    }

    public void decreaseSpeed() {
        if (movementSpeed > 1) {
            movementSpeed -= 1;
        }
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
