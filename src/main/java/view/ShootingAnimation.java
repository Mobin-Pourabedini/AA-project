package view;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import model.Ball;

public class ShootingAnimation extends Transition {
    private Pane pane;
    private Ball centerBall;
    private Ball movingBall;

    public ShootingAnimation(Pane pane, Ball centerBall, Ball movingBall) {
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
            RotatingAnimation animation = new RotatingAnimation(pane, movingBall, (int) centerBall.getCenterX(), (int) centerBall.getCenterY());
            animation.play();
        }
        if (y <= 20) {
            pane.getChildren().remove(movingBall);
            this.stop();
        }
        movingBall.setCenterY(y);
    }
}
