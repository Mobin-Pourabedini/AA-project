package model;

import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import view.RotatingAnimation;

import java.util.List;

public class Ball extends Circle {
    private Line line;

    public Ball(double v) {
        super(v);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
