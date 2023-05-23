package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

public class SettingMenu extends Application {
    public static User loggedInUser;

    public SettingMenu(User loggedInUser) {
        SettingMenu.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(SettingMenu.class.getResource("setting.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }


}
