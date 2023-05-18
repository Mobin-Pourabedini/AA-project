package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import view.ProfileMenu;

public class ProfilePic extends Circle {
    public ProfilePic(User user) {
        super(200);
        super.setFill(user.getAvatar());
        this.setOnMouseClicked(event -> {
            ProfileMenu.getPaint();
        });
    }
}
