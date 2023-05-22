package view;

import controller.DataUtilities;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Aa;

import java.io.IOException;

public class LoginMenu extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        LoginMenu.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Aa.playMusic();
        stage.show();
    }

    public void initialize() throws IOException {
        DataUtilities.fetchData();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    private TextField passwordField;
    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void register() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            welcomeText.setText("Username or password is empty!");
            return;
        }
        if (model.Aa.getUserByUsername(username) != null) {
            welcomeText.setText("Username already exists!");
            return;
        }
        model.Aa.addUser(new model.User(username, password));
        DataUtilities.pushData();
        welcomeText.setText("User registered successfully!");
    }

    @FXML
    protected void login() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        model.User user = model.Aa.getUserByUsername(username);
        if (user == null || !user.isPasswordCorrect(password)) {
            welcomeText.setText("Username or password is incorrect!");
            return;
        }
        MainMenu mainMenu = new MainMenu(user);
        mainMenu.start(LoginMenu.stage);
    }
}