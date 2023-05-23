package model;

import controller.DataUtilities;
import controller.ProfileController;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.ProfileMenu;

import java.io.IOException;

public class AvatarPartition extends VBox {
    private ProfilePic profilePic;

    public AvatarPartition(User user, ProfileController controller) {
        this.setAlignment(Pos.CENTER);
        profilePic = new ProfilePic(user, controller);
        this.getChildren().add(profilePic);
        Label usernameLabel = new Label("username: " + user.getUsername());
        usernameLabel.setAlignment(Pos.CENTER);
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
                Aa.getScoreBoard().getEasyScores().remove(user.getUsername());
                Aa.getScoreBoard().getHardScores().remove(user.getUsername());
                Aa.getScoreBoard().getMediumScores().remove(user.getUsername());
                Aa.removeUser(user.getUsername());
                DataUtilities.pushData();
                DataUtilities.pushScoreBoard();
                ProfileController.exit();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().add(removeButton);
        HBox hBoxChangeName = new HBox();
        Label changeUsername = new Label("change username to ");
        TextField textField = new TextField();
        Button changeUsernameButton = new Button("Apply");
        changeUsernameButton.setOnAction(event -> {
            if (Aa.getUserByUsername(textField.getText()) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("username already exists");
                alert.show();
                return;
            }
            if (Aa.getScoreBoard().getEasyScores().containsKey(user.getUsername())){
                Aa.getScoreBoard().getEasyScores().put(textField.getText(),
                        Aa.getScoreBoard().getEasyScores().get(user.getUsername()));
                Aa.getScoreBoard().getEasyScores().remove(user.getUsername());
            }
            if (Aa.getScoreBoard().getHardScores().containsKey(user.getUsername())){
                Aa.getScoreBoard().getHardScores().put(textField.getText(),
                        Aa.getScoreBoard().getHardScores().get(user.getUsername()));
                Aa.getScoreBoard().getHardScores().remove(user.getUsername());
            }
            if (Aa.getScoreBoard().getMediumScores().containsKey(user.getUsername())){
                Aa.getScoreBoard().getMediumScores().put(textField.getText(),
                        Aa.getScoreBoard().getMediumScores().get(user.getUsername()));
                Aa.getScoreBoard().getMediumScores().remove(user.getUsername());
            }
            user.setUsername(textField.getText());
            usernameLabel.setText("username: " + user.getUsername());
            try {
                DataUtilities.pushData();
                DataUtilities.pushScoreBoard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        hBoxChangeName.getChildren().addAll(changeUsername, textField, changeUsernameButton);
        this.getChildren().add(hBoxChangeName);
        HBox hBoxChangePassword = new HBox();
        Label changePassword = new Label("change password to ");
        TextField textField1 = new TextField();
        Button changePasswordButton = new Button("Apply");
        changePasswordButton.setOnAction(event -> {
            user.setPassword(textField1.getText());
            try {
                DataUtilities.pushData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        hBoxChangePassword.getChildren().addAll(changePassword, textField1, changePasswordButton);
        this.getChildren().add(hBoxChangePassword);
    }

    public ProfilePic getProfilePic() {
        return profilePic;
    }
}
