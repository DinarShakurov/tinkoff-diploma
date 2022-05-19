package ru.tfs.collections_generics.task1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ShowWinner {

    public static void showWinner(List<String> competitors) {
        Map<String,Integer> map = new HashMap<>();

        String[] splitted;
        String name;
        Integer points;

        String maxName = null;
        Integer maxPoints = Integer.MIN_VALUE;
        for (String competitor : competitors) {
            splitted = competitor.split(" ");
            name = splitted[0];
            points = Integer.parseInt(splitted[1]) + map.getOrDefault(name, 0);
            map.put(name, points);

            if (maxPoints < points) {
                maxName = name;
                maxPoints = points;
            }
        }
        System.out.println(maxName);
    }

    public static void main(String[] args) {
        showWinner(asList("Ivan 5", "Petr 3", "Alex 10", "Petr 8", "Ivan 6", "Alex 5", "Ivan 1", "Petr 5", "Alex 1"));
    }
}
