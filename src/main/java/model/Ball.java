package model;

import javafx.animation.Transition;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    private Transition animation;

    public Transition getAnimation() {
        return animation;
    }

    public void setAnimation(Transition animation) {
        this.animation = animation;
    }

    public Ball(double v) {
        super(v);
    }
}
