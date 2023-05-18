package model;

import java.util.ArrayList;
import java.util.List;

public class Aa {
    public static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
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
