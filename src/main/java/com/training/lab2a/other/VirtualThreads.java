package com.training.lab2a.other;

import java.util.List;
import java.util.concurrent.Executors;

public class VirtualThreads {
    public static void main(String[] args) {
        
        //var ids = List.of("C001", "C002", "C003", "C004", "C005", "C006", "C007", "C008", "C009", "C010");
        var ids = java.util.stream.IntStream.rangeClosed(1, 1000000)
                .mapToObj(i -> String.format("C%03d", i))
                .toList();

        var start = System.nanoTime();
        System.out.println("Start: " + java.time.Instant.now());

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            var tasks = ids.stream()
                    .map(id -> pool.submit(() -> fetch(id)))
                    .toList();

            for (var task : tasks) {
                // System.out.println(task.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            var elapsedMillis = (System.nanoTime() - start) / 1_000_000;
            System.out.println("End: " + java.time.Instant.now());
            System.out.println("Elapsed: " + elapsedMillis + " ms");
        }
    }

    private static String fetch(String id) {
        // simulate I/O-bound work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted: " + id;
        }
        return "Fetched: " + id;
    }
}
