package view;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.util.Duration.millis;

public class RotatingBallAnimation extends Application {

    // Number of steps for a circle/ball during one orbit.
    private static final int MOVING_POINTS = 60;
    // Ball speed
    private static final int STEP_DURATION_IN_MILLISECONDS = 10;
    // Scene Size
    private static final int SCENE_SIZE = 800;

    @Override
    public void start(Stage primaryStage) {
        final int midPoint = SCENE_SIZE / 2;

        // Orbits of the balls (Radius)
        final int[] ballOrbits = {300, 200, 100, 50 ,20, 1};
        // Size of the circles
        final int[] ballRadius = {30, 20, 10, 5, 2 ,1 };

        final List<Circle> balls = new ArrayList<>();
        for (int i = 0; i < ballOrbits.length; i++) {
            Circle circle = new Circle(ballRadius[i]);
            circle.setCenterX(0);
            circle.setCenterY(0);
            balls.add(circle);
        }

        final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler() {
            int movingStep = 0;

            @Override
            public void handle(Event event) {
                movingStep++;

                double angleAlpha = movingStep * ( Math.PI / 30 );

                for (int i = 0; i < balls.size(); i++) {
                    // p(x) = x(0) + r * sin(a)
                    // p(y) = y(y) - r * cos(a)
                    moveBall(balls.get(i), midPoint + ballOrbits[i] * Math.sin(angleAlpha), midPoint - ballOrbits[i] * Math.cos(angleAlpha));
                }

                // Reset after one orbit.
                if (movingStep == MOVING_POINTS) {
                    movingStep = 0;
                }
            }
        }), new KeyFrame(Duration.millis(STEP_DURATION_IN_MILLISECONDS)));

        timeline.setCycleCount(Timeline.INDEFINITE);

        Pane root = new Pane();
        root.getChildren().addAll(balls);

        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);

        primaryStage.setTitle("Rotating Balls");
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("rotatingObjects");
        primaryStage.show();

        timeline.play();
    }

    private void moveBall(Circle ball, double x, double y) {
        ball.setCenterY(y);
        ball.setCenterX(x);
    }
}