package controller;

import com.google.gson.Gson;
import model.Aa;
import model.Data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataUtilities {
    public static void pushData() throws IOException {
        Data data = new Data();
        data.setUsers(Aa.getUsers());
        data.setGameMaps(Aa.getGameMaps());
        Gson gson = new Gson();
        String dataString = gson.toJson(data);
        FileWriter fileWriter = new FileWriter("src/main/resources/data/data.json");
        fileWriter.write(dataString);
        fileWriter.close();
    }

    public static void fetchData() throws FileNotFoundException {
        Data data = new Data();
        Gson gson = new Gson();
        Scanner scanner = new Scanner(new FileReader("src/main/resources/data/data.json"));
        String dataString = scanner.nextLine();
        data = gson.fromJson(dataString, Data.class);
        Aa.setUsers(data.getUsers());
        Aa.setGameMaps(data.getGameMaps());
    }
}
