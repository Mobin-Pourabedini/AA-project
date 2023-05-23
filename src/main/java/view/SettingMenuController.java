package view;

import controller.SettingController;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import model.Aa;
import model.User;

import java.io.IOException;

public class SettingMenuController {
    public ComboBox musicInd;
    public ComboBox shootingKey;
    public ComboBox freezingKey;
    public ComboBox pauseKey;
    private SettingController controller;
    public SettingMenuController() {
        this.controller = new SettingController(SettingMenu.getLoggedInUser());
    }

    public void back(ActionEvent event) {
        controller.back();
    }


    public void muteMusic(ActionEvent event) {
        Aa.muteMusic();
    }

    public void unmuteMusic(ActionEvent event) {
        Aa.unmuteMusic();
    }

    public void setMusic(ActionEvent event) {
        String musicStr = (String) musicInd.getValue();
        int index = musicStr.charAt(musicStr.length() - 1) - '0';
        Aa.setMusic(index);
    }

    public void setShootingKey(ActionEvent event) throws IOException {
        String keyStr = (String) shootingKey.getValue();
        controller.setShootingKey(keyStr);
    }

    public void setFreezingKey(ActionEvent event) throws IOException {
        String keyStr = (String) freezingKey.getValue();
        controller.setFreezingKey(keyStr);
    }

    public void setPauseKey(ActionEvent event) throws IOException {
        String keyStr = (String) pauseKey.getValue();
        controller.setPauseKey(keyStr);
    }
}
