package controller;

import javafx.scene.layout.Pane;
import model.Aa;
import model.Ball;
import model.Game;
import view.RotatingAnimation;

import java.util.List;
import java.util.Map;

public class GameController {
    public Game createGame(List<Integer> list, Pane  pane, int totalBallCount, int movementSpeed) {
        Game game = new Game(totalBallCount, movementSpeed);
        for (int pos : list) {
            Ball ball = new Ball(Aa.BALL_RADIOS);
            pane.getChildren().add(ball);
            moveBall(pos, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y,
                    Aa.CENTRAL_BALL_RADIOS + Aa.BALL_RADIOS + 50);
            RotatingAnimation animation = new RotatingAnimation(
                    game, pane, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y);
            ball.setAnimation(animation);
            game.addBall(ball);
            animation.play();
        }
        return game;
    }

    public void reverse(Game game, Pane pane) {
        game.flipDirection();
        for (Ball ball : game.getBalls()) {
            ball.getAnimation().stop();
            ball.setAnimation(new RotatingAnimation(
                    game, pane, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y));
            ball.getAnimation().play();
        }
    }

    public void speedUp(Game game, Pane pane) {
        game.increaseSpeed();
        for (Ball ball : game.getBalls()) {
            ball.getAnimation().stop();
            ball.setAnimation(new RotatingAnimation(
                    game, pane, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y));
            ball.getAnimation().play();
        }
    }

    public void speedDown(Game game, Pane pane) {
        game.decreaseSpeed();
        for (Ball ball : game.getBalls()) {
            ball.getAnimation().stop();
            ball.setAnimation(new RotatingAnimation(
                    game, pane, ball, Aa.CENTRAL_BALL_X, Aa.CENTRAL_BALL_Y));
            ball.getAnimation().play();
        }
    }


    public static void moveBall(int position, Ball ball, int centerX, int centerY, double ballOrbit) {
        double angleAlpha = position * ( (2 * Math.PI) / RotatingAnimation.MOVING_POINTS);
        ball.setCenterX(centerX + ballOrbit * Math.sin(angleAlpha));
        ball.setCenterY(centerY - ballOrbit * Math.cos(angleAlpha));
    }
}
