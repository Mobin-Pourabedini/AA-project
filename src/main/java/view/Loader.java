package view;

import javafx.scene.image.Image;
import model.ProfilePic;
import model.User;

public class Loader {
    public static Image getImage(String path) {
        return new Image(ProfilePic.class.getResource(path).toExternalForm());
    }
}
