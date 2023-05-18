package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.Ball;
import model.Game;

public class ShootingAnimation extends Transition {
    private Pane pane;
    private Ball centerBall;
    private Ball movingBall;
    private model.Game game;
    private double degree;

    public ShootingAnimation(Game game, Pane pane, Ball centerBall, Ball movingBall) {
        this.game = game;
        this.degree = game.getAngle();
        this.pane = pane;
        this.centerBall = centerBall;
        this.movingBall = movingBall;
        this.setCycleDuration(javafx.util.Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = movingBall.getCenterY() - Math.cos(Math.toRadians(degree));
        double x = movingBall.getCenterX() + Math.sin(Math.toRadians(degree));
        if (checkIntersection(movingBall, centerBall)) {
            this.stop();
            game.addBall(movingBall);
            RotatingAnimation animation = new RotatingAnimation(game, pane, movingBall, (int) centerBall.getCenterX(), (int) centerBall.getCenterY());
            animation.play();
        }else if (game.getBalls().stream().anyMatch(ball -> checkIntersection(movingBall, ball))) {
            this.stop();
            GameMenu.endGame();
        }

        movingBall.setCenterY(y);
        movingBall.setCenterX(x);
    }

    private boolean checkIntersection(Ball movingBall, Ball centerBall) {
        double distance = Math.sqrt(Math.pow(movingBall.getCenterX() - centerBall.getCenterX(), 2) + Math.pow(movingBall.getCenterY() - centerBall.getCenterY(), 2));
        return distance <= movingBall.getRadius() + centerBall.getRadius();
    }
}
