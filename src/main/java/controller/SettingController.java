package controller;

import model.Data;
import model.User;
import view.LoginMenu;
import view.MainMenu;

import java.io.IOException;

public class SettingController {
    private final User loggedInUser;

    public SettingController(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void back() {
        MainMenu mainMenu = new MainMenu(loggedInUser);
        try {
            mainMenu.start(LoginMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setShootingKey(String keyStr) throws IOException {
        loggedInUser.setShootingKey(keyStr);
        DataUtilities.pushData();
    }

    public void setFreezingKey(String keyStr) throws IOException {
        loggedInUser.setFreezingKey(keyStr);
        DataUtilities.pushData();
    }

    public void setPauseKey(String keyStr) throws IOException {
        loggedInUser.setPauseKey(keyStr);
        DataUtilities.pushData();
    }
}
