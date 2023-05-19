package controller;

import javafx.scene.layout.Pane;
import model.Aa;
import model.Ball;
import model.Game;
import view.RotatingAnimation;

import java.util.Map;

public class GameController {
    public Game createGame(Map<Ball, Integer> map, Pane  pane, int totalBallCount, int movementSpeed) {
        Game game = new Game(totalBallCount, movementSpeed);
        for (Map.Entry<Ball, Integer> entry : map.entrySet()) {
            Ball ball = entry.getKey();
            int pos = entry.getValue();
            pane.getChildren().add(ball);
            moveBall(pos, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y,
                    Aa.CENTRAL_BALL_RADIOS + Aa.BALL_RADIOS);
            RotatingAnimation animation = new RotatingAnimation(
                    game, pane, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
            animation.play();
        }
        return game;
    }


    public static void moveBall(int position, Ball ball, int centerX, int centerY, double ballOrbit) {
        double angleAlpha = position * ( (2 * Math.PI) / RotatingAnimation.MOVING_POINTS);
        ball.setCenterX(centerX + ballOrbit * Math.sin(angleAlpha));
        ball.setCenterY(centerY - ballOrbit * Math.cos(angleAlpha));
    }
}
