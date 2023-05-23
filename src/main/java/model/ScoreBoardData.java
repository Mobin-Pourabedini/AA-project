package model;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoardData {
    private Map<String, Integer> easyScores = new HashMap<>(),
            mediumScores = new HashMap<>(), hardScores = new HashMap<>();


    public Map<String, Integer> getEasyScores() {
        return easyScores;
    }

    public Map<String, Integer> getMediumScores() {
        return mediumScores;
    }

    public Map<String, Integer> getHardScores() {
        return hardScores;
    }

    public void setEasyScores(Map<String, Integer> easyScores) {
        this.easyScores = easyScores;
    }

    public void setMediumScores(Map<String, Integer> mediumScores) {
        this.mediumScores = mediumScores;
    }

    public void setHardScores(Map<String, Integer> hardScores) {
        this.hardScores = hardScores;
    }
}
