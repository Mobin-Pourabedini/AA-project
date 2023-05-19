package model;

import java.util.List;
import java.util.Map;

public class Data {
    private List<Map<Ball, Integer>> gameMaps;

    public Data() {
        gameMaps = new java.util.ArrayList<>();
    }

    public List<Map<Ball, Integer>> getGameMaps() {
        return gameMaps;
    }

    public void addGameMap(Map<Ball, Integer> map) {
        gameMaps.add(map);
    }
}
