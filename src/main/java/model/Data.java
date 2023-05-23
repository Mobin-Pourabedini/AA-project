package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private List<List<Integer>> gameMaps = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<String, Integer> easyScores = new HashMap<>(),
            mediumScores = new HashMap<>(), hardScores = new HashMap<>();

    public Data() {
        gameMaps = new java.util.ArrayList<>();
    }

    public List<List<Integer>> getGameMaps() {
        return gameMaps;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setGameMaps(List<List<Integer>> gameMaps) {
        this.gameMaps = gameMaps;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addGameMap(List<Integer> list) {
        gameMaps.add(list);
    }
}
