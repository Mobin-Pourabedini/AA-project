package model;

import controller.ProfileController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import view.ProfileMenu;

import java.io.IOException;

public class AvatarPartition extends VBox {
    private ProfilePic profilePic;

    public AvatarPartition(User user, ProfileController controller) {
        this.setAlignment(javafx.geometry.Pos.CENTER);
        profilePic = new ProfilePic(user, controller);
        this.getChildren().add(profilePic);
        Label usernameLabel = new Label("username: " + user.getUsername());
        usernameLabel.setAlignment(javafx.geometry.Pos.CENTER);
        this.getChildren().add(usernameLabel);
        Button button = new Button("change avatar");
        button.setOnAction(event -> {
            controller.getPaint();
        });
        this.getChildren().add(button);
        Button exitButton = new Button("exit");
        exitButton.setOnAction(event -> {
            try {
                ProfileController.exit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().add(exitButton);
        Button removeButton = new Button("delete account and exit");
        removeButton.setOnAction(event -> {
            Aa.getUsers().remove(user);
            try {
                ProfileController.exit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().add(removeButton);
    }

    public ProfilePic getProfilePic() {
        return profilePic;
    }
}
