package view;

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
    public static User loggedInUser;
    private static ProfilePic profilePic;

    public static void exit() throws IOException {
        new LoginMenu().start(LoginMenu.stage);
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ProfileMenu.class.getResource("profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        AvatarPartition avatar = new AvatarPartition(loggedInUser);
        profilePic = avatar.getProfilePic();
        avatarPartition.getChildren().add(avatar);
    }

    public static void getPaint() {
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
                loggedInUser.setProfilePic(paint);
                profilePic.setFill(paint);
                System.out.println("clicked" + index);
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
}
