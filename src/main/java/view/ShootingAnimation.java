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

    public ShootingAnimation(Game game, Pane pane, Ball centerBall, Ball movingBall) {
        this.game = game;
        this.pane = pane;
        this.centerBall = centerBall;
        this.movingBall = movingBall;
        this.setCycleDuration(javafx.util.Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = movingBall.getCenterY() - 1;
        if (movingBall.getBoundsInParent().intersects(centerBall.getLayoutBounds())) {
            this.stop();
            game.addBall(movingBall);
            RotatingAnimation animation = new RotatingAnimation(pane, movingBall, (int) centerBall.getCenterX(), (int) centerBall.getCenterY());
            animation.play();
        }else if (game.getBalls().stream().anyMatch(ball -> movingBall.getBoundsInParent().intersects(ball.getLayoutBounds()))) {
            this.stop();
            GameMenu.endGame();
        }

        movingBall.setCenterY(y);
    }
}
