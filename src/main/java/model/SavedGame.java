package model;

import java.util.List;

public class SavedGame {
    private int phaseNum;
    private int remainingBalls;
    private int numberOfPins;
    private List<Integer> gameMap;
    private int difficulty, score;

    public SavedGame(int phaseNum, int remainingBalls, int numberOfPins, List<Integer> gameMap) {
        this.phaseNum = phaseNum;
        this.remainingBalls = remainingBalls;
        this.numberOfPins = numberOfPins;
        this.gameMap = gameMap;
    }

    public int getPhaseNum() {
        return phaseNum;
    }

    public int getRemainingBalls() {
        return remainingBalls;
    }

    public int getNumberOfPins() {
        return numberOfPins;
    }

    public List<Integer> getGameMap() {
        return gameMap;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }
}
