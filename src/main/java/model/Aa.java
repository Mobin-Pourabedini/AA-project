package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aa {
    public static final int CENTRAL_BALL_RADIOS = 150, BALL_RADIOS = 10;
    public static final int SCENE_SIZE = 600;
    public static final int CENTRAL_BALL_X = SCENE_SIZE / 2, CENTRAL_BALL_Y = SCENE_SIZE / 2 - 100;
    private static List<User> users = new ArrayList<>();
    private static List<Map<Ball, Integer>> gameMaps = new ArrayList<>();


    public static void addUser(User user) {
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void addGameMap(Map<Ball, Integer> map) {
        gameMaps.add(map);
    }

    public static Map<Ball, Integer> getGameMap(int index) {
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
}
