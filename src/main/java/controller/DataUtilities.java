package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Aa;
import model.Data;
import model.ScoreBoardData;
import view.MainMenu;

import java.io.*;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataUtilities {
    public static void pushData() throws IOException {
        Data data = new Data();
//        ScoreBoardData scoreBoardData = new ScoreBoardData();
        data.setUsers(Aa.getUsers());
        data.setGameMaps(Aa.getGameMaps());
//        scoreBoardData.setEasyScores(MainMenu.getScoreBoard().getEasyScores());
//        scoreBoardData.setMediumScores(MainMenu.getScoreBoard().getMediumScores());
//        scoreBoardData.setHardScores(MainMenu.getScoreBoard().getHardScores());
        Gson gson = new Gson();
        String dataString = gson.toJson(data);
        FileWriter fileWriter = new FileWriter("src/main/resources/data/data.json");
        fileWriter.write(dataString);
        fileWriter.close();
    }

    public static void pushScoreBoard() throws IOException {
        Gson gson = new Gson();
        Map<String, Integer> map = Aa.getScoreBoard().getEasyScores();
        String dataString1 = gson.toJson(map);
        String dataString2 = gson.toJson(Aa.getScoreBoard().getMediumScores());
        String dataString3 = gson.toJson(Aa.getScoreBoard().getHardScores());
        PrintStream printStream = new PrintStream(new FileOutputStream("src/main/resources/data/scoreBoardData.json"));
        printStream.println(dataString1);
        printStream.println(dataString2);
        printStream.println(dataString3);
    }

    public static void fetchData() throws FileNotFoundException {
        Data data = new Data();
//        ScoreBoardData scoreBoardData = new ScoreBoardData();
        Gson gson = new Gson();
        Scanner scanner = new Scanner(new FileReader("src/main/resources/data/data.json"));
        String dataString = scanner.nextLine();
//        Scanner scoreScanner = new Scanner(new FileReader("src/main/resources/data/scoreBoardData.json"));
        data = gson.fromJson(dataString, Data.class);
//        scoreBoardData = gson.fromJson(scoreScanner.nextLine(), ScoreBoardData.class);
        Aa.setUsers(data.getUsers());
        Aa.setGameMaps(data.getGameMaps());
//        MainMenu.getScoreBoard().setEasyScores(scoreBoardData.getEasyScores());
//        MainMenu.getScoreBoard().setMediumScores(scoreBoardData.getMediumScores());
//        MainMenu.getScoreBoard().setHardScores(scoreBoardData.getHardScores());
    }

    public static void fetchScoreBoard() throws FileNotFoundException {
        Gson gson = new Gson();
        Scanner scanner = new Scanner(new FileReader("src/main/resources/data/scoreBoardData.json"));
        String dataString1 = scanner.nextLine();
        String dataString2 = scanner.nextLine();
        String dataString3 = scanner.nextLine();
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> map1 = gson.fromJson(dataString1, type);
        Map<String, Integer> map2 = gson.fromJson(dataString2, type);
        Map<String, Integer> map3 = gson.fromJson(dataString3, type);
        Aa.getScoreBoard().setEasyScores(map1);
        Aa.getScoreBoard().setMediumScores(map2);
        Aa.getScoreBoard().setHardScores(map3);
    }
}
