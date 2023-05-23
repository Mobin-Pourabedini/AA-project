package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;

public class ScoreBoard {
    private final Map<User, Integer> easyScores = new HashMap<>();
    private final Map<User, Integer> mediumScores = new HashMap<>();
    private final Map<User, Integer> hardScores = new HashMap<>();
    private VBox vBox;

    public ScoreBoard() {
        this.vBox = new VBox();
    }

    public void addScore(User user, int score, int difficulty) {
        switch (difficulty) {
            case 1:
                easyScores.put(user, score);
                break;
            case 2:
                mediumScores.put(user, score);
                break;
            case 3:
                hardScores.put(user, score);
                break;
        }
    }

    public VBox getScoreBoard(int difficulty) {
        this.vBox.getChildren().clear();
        Map<User, Integer> scores;
        switch (difficulty) {
            case 1:
                scores = easyScores;
                break;
            case 2:
                scores = mediumScores;
                break;
            default:
                scores = hardScores;
                break;
        }
        VBox scoreBoard = this.vBox;
        ComboBox comboBox = new ComboBox<String>();
        comboBox.getItems().add("Easy");
        comboBox.getItems().add("Medium");
        comboBox.getItems().add("Hard");
        comboBox.setOnAction(event ->  {
            if (comboBox.getValue().equals("Easy")) {
                getScoreBoard(1);
            } else if (comboBox.getValue().equals("Medium")) {
                getScoreBoard(2);
            } else {
                getScoreBoard(3);
            }
        });
        scoreBoard.getChildren().add(comboBox);
        List<User> users = new ArrayList<>(scores.keySet());
        Collections.sort(users, Comparator.comparingInt(scores::get).reversed());
        int index = 1;
        for (User user : users) {
            scoreBoard.getChildren().add(formatUser(user, index++));
            if (index > 10)break;
        }
        scoreBoard.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        scoreBoard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return scoreBoard;
    }

    private HBox formatUser(User user, int index) {
        HBox userBox = new HBox();
        Label rank = new Label(String.valueOf(index));
        rank.setFont(new Font("Arial", 20));
        rank.setMinWidth(60);
        rank.setMaxWidth(60);
        rank.setAlignment(javafx.geometry.Pos.CENTER);
        userBox.getChildren().add(rank);
        ProfilePic pic = new ProfilePic(user);
        userBox.getChildren().add(pic);
        Label username = new Label(user.getUsername());
        username.setFont(new Font("Arial", 14));
        userBox.setAlignment(javafx.geometry.Pos.CENTER);
        userBox.getChildren().add(username);
        username.setMinWidth(80);
        username.setMaxWidth(80);
        Label label = new Label(String.valueOf(easyScores.get(user)));
        label.setFont(new Font("Arial", 20));
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setMinWidth(80);
        label.setMaxWidth(80);
        userBox.getChildren().add(label);
        userBox.setSpacing(30);
        userBox.setAlignment(javafx.geometry.Pos.CENTER);
        userBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT)));
        if (index == 1) {
            userBox.setBackground(new Background(new BackgroundFill(Color.GOLD, new CornerRadii(20), Insets.EMPTY)));
            return userBox;
        }
        if (index == 2) {
            userBox.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(20), Insets.EMPTY)));
            return userBox;
        }
        if (index == 3) {
            userBox.setBackground(new Background(new BackgroundFill(Color.BROWN, new CornerRadii(20), Insets.EMPTY)));
            return userBox;
        }
        userBox.setBackground(new Background(new BackgroundFill(Color.BISQUE, new CornerRadii(20), Insets.EMPTY)));
        return userBox;
    }
}
