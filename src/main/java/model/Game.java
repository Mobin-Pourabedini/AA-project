package model;

import javafx.scene.layout.Pane;
import view.GameMenu;

import java.util.List;

public class Game {

    private int totalBallCount;
    private int currentBallCount;
    List<Ball> balls = new java.util.ArrayList<>();
    private Ball[] ballsArray;

    public Game(int totalBallCount) {
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
}
