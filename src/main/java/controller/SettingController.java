package controller;

import model.User;
import view.LoginMenu;
import view.MainMenu;

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


    public void setShootingKey(String keyStr) {
        loggedInUser.setShootingKey(keyStr);
    }

    public void setFreezingKey(String keyStr) {
        loggedInUser.setFreezingKey(keyStr);
    }

    public void setPauseKey(String keyStr) {
        loggedInUser.setPauseKey(keyStr);
    }
}
