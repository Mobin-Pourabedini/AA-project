package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

public class MainMenu extends Application {
    public Button startNewGame;
    public Button loadGame;
    public Button profileButton;
    public Button settingsButton;
    private User loggedInUser;
    public GridPane grid;

    public MainMenu(User user) {
        loggedInUser = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("main.fxml"));
        this.grid = new GridPane();
        Button startNewGameButton = new Button("Start new game");
        startNewGameButton.setOnAction((ActionEvent event) -> {
            try {
                startGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button loadGameButton = new Button("Continue playing");
        Button profileButton = new Button("Profile");
        profileButton.setOnAction((ActionEvent event) -> {
            try {
                ProfileMenu profileMenu = new ProfileMenu(loggedInUser);
                profileMenu.start(LoginMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button settingsButton = new Button("Settings");
        grid.add(startNewGameButton, 0, 0);
        grid.add(loadGameButton, 1, 0);
        grid.add(profileButton, 0, 1);
        grid.add(settingsButton, 1, 1);
        pane.getChildren().add(grid);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void startGame() throws Exception {
        StartGameMenu startGameMenu = new StartGameMenu(loggedInUser);
        startGameMenu.start(LoginMenu.stage);
    }
}
