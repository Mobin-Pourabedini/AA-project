package model;

import controller.DataUtilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.*;

public class ScoreBoard {
    private Map<String, Integer> easyScores = new HashMap<>();
    private Map<String, Integer> mediumScores = new HashMap<>();
    private Map<String, Integer> hardScores = new HashMap<>();
    private VBox vBox;
    private int difficulty;

    public ScoreBoard() {
        this.vBox = new VBox();
    }

    public Map<String, Integer> getEasyScores() {
        return easyScores;
    }

    public Map<String, Integer> getMediumScores() {
        return mediumScores;
    }

    public Map<String, Integer> getHardScores() {
        return hardScores;
    }

    public void setEasyScores(Map<String, Integer> easyScores) {
        this.easyScores = easyScores;
    }

    public void setMediumScores(Map<String, Integer> mediumScores) {
        this.mediumScores = mediumScores;
    }

    public void setHardScores(Map<String, Integer> hardScores) {
        this.hardScores = hardScores;
    }

    public void addScore(User user, int score, int difficulty) throws IOException {
        switch (difficulty) {
            case 1:
                score = Math.max(score, easyScores.getOrDefault(user.getUsername(), 0));
                easyScores.put(user.getUsername(), score);
                break;
            case 2:
                score = Math.max(score, easyScores.getOrDefault(user.getUsername(), 0));
                mediumScores.put(user.getUsername(), score);
                break;
            case 3:
                score = Math.max(score, easyScores.getOrDefault(user.getUsername(), 0));
                hardScores.put(user.getUsername(), score);
                break;
        }
        getScoreBoard(difficulty);
        DataUtilities.pushData();
    }

    public VBox getScoreBoard(int difficulty) {
        this.difficulty = difficulty;
        this.vBox.getChildren().clear();
        Map<String, Integer> scores;
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
        List<String> usernames = new ArrayList<>(scores.keySet());
        Collections.sort(usernames, Comparator.comparingInt(scores::get).reversed());
        List<User> users = usernames.stream().map(Aa::getUserByUsername).toList();
        int index = 1;
        for (User user : users) {
            scoreBoard.getChildren().add(formatUser(user, index++, difficulty));
            if (index > 10)break;
        }
        scoreBoard.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        scoreBoard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return scoreBoard;
    }

    private HBox formatUser(User user, int index, int difficulty) {
        Map<String, Integer> scores;
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
        Label label = new Label(String.valueOf(scores.get(user.getUsername())));
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
