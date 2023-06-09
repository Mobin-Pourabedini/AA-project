package controller;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import model.AvatarPartition;
import model.ProfilePic;
import model.User;
import view.LoginMenu;
import view.ProfileMenu;

import java.io.IOException;

public class ProfileController {
    private User loggedInUser;
    private ProfilePic profilePic;

    public ProfileController(User user) {
        loggedInUser = user;
    }

    public void getPaint() {
        Popup popup = new Popup();
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("choose your avatar"));
        HBox hBox = new HBox();

        for (int i = 0; i < 6; i++) {
            Paint paint = new ImagePattern(new Image(ProfileMenu.class.getResource(
                    "/images/Untitled design ("+i+").png").toExternalForm()));
            Rectangle rectangle = new Rectangle(200, 300, paint);
            final int index = i;
            rectangle.setOnMouseClicked(event -> {
                loggedInUser.setProfilePic("/images/Untitled design ("+index+").png");
                profilePic.setFill(paint);
                System.out.println("clicked" + index);
                try {
                    DataUtilities.pushData();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                popup.hide();
            });
            hBox.getChildren().add(rectangle);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);
        scrollPane.setPrefSize(600, 300);
        vBox.getChildren().add(scrollPane);
        popup.getContent().add(vBox);
        popup.show(LoginMenu.stage);
    }

    public AvatarPartition createAvatarPartition() {
        AvatarPartition avatar = new AvatarPartition(loggedInUser, this);
        this.profilePic = avatar.getProfilePic();
        return avatar;
    }

    public static void exit() throws IOException {
        new LoginMenu().start(LoginMenu.stage);
    }
}
