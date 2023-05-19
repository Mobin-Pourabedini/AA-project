package view;

import model.Aa;
import model.Ball;
import model.Data;

import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        Map<Ball, Integer> map = new HashMap<>();
        map.put(new Ball(Aa.BALL_RADIOS), 100);
        map.put(new Ball(Aa.BALL_RADIOS), 200);
        map.put(new Ball(Aa.BALL_RADIOS), 800);
        map.put(new Ball(Aa.BALL_RADIOS), 1100);
        map.put(new Ball(Aa.BALL_RADIOS), 1200);
        map.put(new Ball(Aa.BALL_RADIOS), 1700);
        map.put(new Ball(Aa.BALL_RADIOS), 2000);
        Data data = new Data();
        data.addGameMap(map);
        map.clear();
        map.put(new Ball(Aa.BALL_RADIOS), 100);
        map.put(new Ball(Aa.BALL_RADIOS), 200);
        map.put(new Ball(Aa.BALL_RADIOS), 300);
        map.put(new Ball(Aa.BALL_RADIOS), 400);
        map.put(new Ball(Aa.BALL_RADIOS), 1200);
        map.put(new Ball(Aa.BALL_RADIOS), 1300);
        map.put(new Ball(Aa.BALL_RADIOS), 1400);
        map.put(new Ball(Aa.BALL_RADIOS), 1500);
        map.put(new Ball(Aa.BALL_RADIOS), 1600);
        data.addGameMap(map);
        map.clear();
        map.put(new Ball(Aa.BALL_RADIOS), 100);
        map.put(new Ball(Aa.BALL_RADIOS), 200);
        map.put(new Ball(Aa.BALL_RADIOS), 300);
        map.put(new Ball(Aa.BALL_RADIOS), 400);
        map.put(new Ball(Aa.BALL_RADIOS), 500);
        map.put(new Ball(Aa.BALL_RADIOS), 600);
        map.put(new Ball(Aa.BALL_RADIOS), 700);
        map.put(new Ball(Aa.BALL_RADIOS), 800);
        map.put(new Ball(Aa.BALL_RADIOS), 900);
        data.addGameMap(map);
    }
}
