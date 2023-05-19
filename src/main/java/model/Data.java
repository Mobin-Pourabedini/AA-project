package model;

import java.util.List;
import java.util.Map;

public class Data {
    private List<List<Integer>> gameMaps;
    private List<User> users;

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
