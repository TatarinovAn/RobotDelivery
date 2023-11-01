package ru.netology;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                int count = 0;
                String way = generateRoute("RLRFR", 100);
                for (int j = 0; j < way.length(); j++) {
                    if (way.charAt(j) == 'R') {
                        count++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        int howManyTimes = sizeToFreq.get(count);
                        howManyTimes++;
                        sizeToFreq.put(count, howManyTimes);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            }).start();

        }

        int maxKey = sizeToFreq.keySet().stream().max(Comparator.comparing(sizeToFreq::get)).orElse(null);
        System.out.println("Самое частое количество повторений: " + maxKey + " (встретилось " +
                sizeToFreq.get(maxKey) + " раз)");
        sizeToFreq.remove(maxKey);
        System.out.println("Другие повторения");
        for (int key : sizeToFreq.keySet()) {
            System.out.println("- " + key + " (" + sizeToFreq.get(key) + " раз)");
        }

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}