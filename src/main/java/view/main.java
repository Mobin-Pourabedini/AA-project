package view;

import com.google.gson.Gson;
import model.Aa;
import model.Ball;
import model.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) throws IOException {
        Data data = new Data();
        List<Integer> list = List.of(100, 200, 800, 1100, 1200, 1700, 2000);
        data.addGameMap(list);
        list = List.of(100, 200, 300, 400, 1200, 1300, 1400, 1500, 1600);
        data.addGameMap(list);
        list = List.of(100, 200, 300, 400, 500, 600, 700, 800, 900);
        data.addGameMap(list);
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter("src/main/resources/data/data.json");
        String jsonToBePushed = gson.toJson(data);
        fileWriter.write(jsonToBePushed);
        fileWriter.close();
    }
}
