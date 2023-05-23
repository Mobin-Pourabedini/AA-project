package model;

import controller.GameController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.GameMenu;
import view.MainMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aa {
    public static final int CENTRAL_BALL_RADIOS = 80, BALL_RADIOS = 7;
    public static final int SCENE_SIZE = 600;
    public static final int CENTRAL_BALL_X = SCENE_SIZE / 2, CENTRAL_BALL_Y = SCENE_SIZE / 2 - 100;
    private static List<User> users = new ArrayList<>();
    private static List<List<Integer>> gameMaps = new ArrayList<>();
    public static boolean isInPhase1 = false;
    public static boolean isInPhase2 = false;
    public static boolean isInPhase3 = false;
    public static boolean isInPhase4 = false;

    private static GameController gameController;


    private static Media media = new Media(Aa.class.getResource("/media/music0.mp3").toString());
    private static MediaPlayer mediaPlayer = new MediaPlayer(media);
    private static ScoreBoard scoreBoard = new ScoreBoard();


    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void addGameMap(List<Integer> map) {
        gameMaps.add(map);
    }

    public static GameController getSavedGame() {
        return gameController;
    }

    public static List<List<Integer>> getGameMaps() {
        return gameMaps;
    }

    public static List<Integer> getGameMap(int index) {
        return gameMaps.get(index);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public static void setUsers(List<User> users) {
        Aa.users = users;
    }

    public static void setGameMaps(List<List<Integer>> gameMaps) {
        Aa.gameMaps = gameMaps;
    }

    public static void playMusic() {
        mediaPlayer.setCycleCount(-1);
        mediaPlayer.play();
    }

    public static void setMusic(int index) {
        mediaPlayer.stop();
        media = new Media(Aa.class.getResource("/media/music" + index + ".mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        playMusic();
    }

    public static void muteMusic() {
        mediaPlayer.setMute(true);
    }

    public static void unmuteMusic() {
        mediaPlayer.setMute(false);
    }

    public static void saveGame(GameController controller) {
        gameController = controller;
    }

    public static void removeUser(String username) {
        users.remove(getUserByUsername(username));
    }
}
