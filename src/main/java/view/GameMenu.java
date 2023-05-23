package view;

import controller.DataUtilities;
import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Aa;
import model.Ball;
import model.Game;
import model.User;

public class GameMenu extends Application {
    private final GameController controller;
    public final static int SCENE_SIZE = 600, middle = SCENE_SIZE / 2;
    private Ball central;
    private int  remainingBalls;
    private Pane gamePane;
    private ProgressBar freezeBar;
    private Label freezePower, phaseLabel, degreeLabel, remainingBallsText;

    public GameMenu(User loggedInUser, int difficulty, int numberOfPins, int gameIndex) {
        this.gamePane = new Pane();
        this.controller = new GameController(
                this, loggedInUser, gamePane, numberOfPins, difficulty, gameIndex);
        this.remainingBalls = numberOfPins;
        this.central = controller.createCentralBall();
        gamePane.getChildren().add(central);
        this.remainingBallsText = new Label("Remaining balls: " + remainingBalls);
        remainingBallsText.setLayoutX(10);remainingBallsText.setLayoutY(20);
        remainingBallsText.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(remainingBallsText);
        freezeBar = new ProgressBar();
        freezeBar.setLayoutX(70);freezeBar.setLayoutY(Aa.SCENE_SIZE - 20);
        freezeBar.setProgress(0);
        freezeBar.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        freezeBar.setStyle("-fx-accent: gold;");
        gamePane.getChildren().add(freezeBar);
        Label freezeLabel = new Label("Freeze");
        freezeLabel.setLayoutX(30);freezeLabel.setLayoutY(Aa.SCENE_SIZE - 50);
        freezeLabel.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(freezeLabel);
        freezePower = new Label((int) (controller.getFreezePower()*100) + "%");
        freezePower.setLayoutX(10);freezePower.setLayoutY(Aa.SCENE_SIZE - 25);
        freezePower.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(freezePower);
        phaseLabel = new Label("Phase 1");
        phaseLabel.setLayoutX(Aa.SCENE_SIZE - 100);phaseLabel.setLayoutY(20);
        phaseLabel.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(phaseLabel);
        degreeLabel = new Label("degree: 0");
        degreeLabel.setLayoutX(Aa.SCENE_SIZE - 120);degreeLabel.setLayoutY(Aa.SCENE_SIZE - 50);
        degreeLabel.setFont(new Font("Arial", 20));
        gamePane.getChildren().add(degreeLabel);
    }
    
    public GameMenu(GameController controller) {
        this.controller = controller;
        this.gamePane = new Pane();
        this.gamePane.getChildren().addAll(controller.getPane().getChildren());
        controller.setPane(gamePane);
        this.central = controller.getCentral();
        this.remainingBalls = controller.getRemainingBalls();
        this.controller.resumeGame();
//        gamePane.getChildren().add(central);
//        this.remainingBallsText = new Label("Remaining balls: " + remainingBalls);
//        remainingBallsText.setLayoutX(10);remainingBallsText.setLayoutY(20);
//        remainingBallsText.setFont(new Font("Arial", 20));
//        gamePane.getChildren().add(remainingBallsText);
//        freezeBar = new ProgressBar();
//        freezeBar.setLayoutX(70);freezeBar.setLayoutY(Aa.SCENE_SIZE - 20);
//        freezeBar.setProgress(controller.getFreezePower());
//        freezeBar.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//        freezeBar.setStyle("-fx-accent: gold;");
//        gamePane.getChildren().add(freezeBar);
//        Label freezeLabel = new Label("Freeze");
//        freezeLabel.setLayoutX(30);freezeLabel.setLayoutY(Aa.SCENE_SIZE - 50);
//        freezeLabel.setFont(new Font("Arial", 20));
//        gamePane.getChildren().add(freezeLabel);
//        freezePower = new Label((int) (controller.getFreezePower()*100) + "%");
//        freezePower.setLayoutX(10);freezePower.setLayoutY(Aa.SCENE_SIZE - 25);
//        freezePower.setFont(new Font("Arial", 20));
//        gamePane.getChildren().add(freezePower);
//        phaseLabel = new Label("Phase " + controller.getPhase());
//        if (controller.getPhase() >= 2) {
//            controller.enterPhase2();
//        }
//        if (controller.getPhase() >= 3) {
//            controller.enterPhase3();
//        }
//        if (controller.getPhase() >= 4) {
//            controller.enterPhase4();
//        }
//        phaseLabel = new Label("Phase 1");
//        phaseLabel.setLayoutX(Aa.SCENE_SIZE - 100);phaseLabel.setLayoutY(20);
//        phaseLabel.setFont(new Font("Arial", 20));
//        gamePane.getChildren().add(phaseLabel);
//        degreeLabel = new Label("degree: 0");
//        degreeLabel.setLayoutX(Aa.SCENE_SIZE - 120);degreeLabel.setLayoutY(Aa.SCENE_SIZE - 50);
//        degreeLabel.setFont(new Font("Arial", 20));
//        gamePane.getChildren().add(degreeLabel);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DataUtilities.fetchData();
        LoginMenu.stage = stage;
        Scene scene = new Scene(gamePane, SCENE_SIZE, SCENE_SIZE);
        stage.setScene(scene);
        central.requestFocus();
        stage.show();
    }

    public void addToProgress() {
        if (freezeBar.getProgress() >= 1) {
            return;
        }
        freezeBar.setProgress(freezeBar.getProgress() + 0.1001);
        freezePower.setText((int) (freezeBar.getProgress() * 100) + "%");
    }

    public boolean checkFreeze() {
        if (freezeBar.getProgress() >= 1) {
            freezeBar.setProgress(0);
            freezePower.setText("0%");
            return true;
        }
        return false;
    }

    public void setPhaseLabel(String text) {
        phaseLabel.setText(text);
    }

    public void setDegreeLabel(String text) {
        degreeLabel.setText(text);
    }

    public void setRemainingBallsText(String text) {
        remainingBallsText.setText(text);
    }

}
