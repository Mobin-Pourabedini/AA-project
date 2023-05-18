package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class User {
    private String username;
    private String password;
    private Paint avatar;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.avatar = new ImagePattern(
                new Image(ProfilePic.class.getResource(
                        "/images/Untitled design (0).png").toExternalForm()));
    }

    public String getUsername() {
        return username;
    }

    public Paint getAvatar() {
        return avatar;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void setProfilePic(Paint paint) {
        this.avatar = paint;
    }
}
