package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

public class User {
    private String username;
    private String password;
    private String avatarPath;
    String shootingKey, freezingKey, pauseKey;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.avatarPath = "/images/Untitled design (0).png";
    }

    public String getUsername() {
        return username;
    }

    public Paint getAvatar() {
        return new ImagePattern(
                new Image(ProfilePic.class.getResource(
                        this.avatarPath).toExternalForm()));
    }

    public String getShootingKey() {
        return shootingKey;
    }

    public String getFreezingKey() {
        return freezingKey;
    }

    public String getPauseKey() {
        return pauseKey;
    }

    public void setShootingKey(String shootingKey) {
        this.shootingKey = shootingKey;
    }

    public void setFreezingKey(String freezingKey) {
        this.freezingKey = freezingKey;
    }

    public void setPauseKey(String pauseKey) {
        this.pauseKey = pauseKey;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void setProfilePic(String avatarPath) {
        this.avatarPath = avatarPath;
    }


}
