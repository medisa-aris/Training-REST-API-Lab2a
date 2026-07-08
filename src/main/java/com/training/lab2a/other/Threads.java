package com.training.lab2a.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    public static void main(String[] args) {

        //var ids = List.of("C001", "C002", "C003", "C004", "C005", "C006", "C007", "C008", "C009", "C010");
        var ids = java.util.stream.IntStream.rangeClosed(1, 1000000)
                .mapToObj(i -> String.format("C%03d", i))
                .toList();

        int poolSize = 100;
        long start = System.currentTimeMillis();
        System.out.println("Start time: " + start + " ms");

        try (ExecutorService pool = Executors.newFixedThreadPool(poolSize)) {
            var tasks = ids.stream()
                    .map(id -> pool.submit(() -> fetch(id)))
                    .toList();

            for (var task : tasks) {
                //System.out.println(task.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long finish = System.currentTimeMillis();
            System.out.println("Finish time: " + finish + " ms");
            System.out.println("Elapsed time: " + (finish - start) + " ms");
        }
    }

    private static String fetch(String id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted: " + id;
        }
        return "Fetched: " + id;
    }
}
