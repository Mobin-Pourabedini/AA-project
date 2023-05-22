package view;

import controller.DataUtilities;
import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.AvatarPartition;
import model.ProfilePic;
import model.User;

import java.io.IOException;

public class ProfileMenu extends Application {
    public VBox avatarPartition;
    private final User loggedInUser;
    private ProfileController controller;

    public ProfileMenu(User user) {
        loggedInUser = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.controller = new ProfileController(loggedInUser);
        VBox avatarPartition = new VBox();
        Scene scene = new Scene(avatarPartition);
        AvatarPartition avatar = this.controller.createAvatarPartition();
        avatarPartition.getChildren().add(avatar);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }
}
