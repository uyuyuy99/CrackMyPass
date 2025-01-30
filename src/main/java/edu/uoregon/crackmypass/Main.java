package edu.uoregon.crackmypass;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static ExecutorService bruteForceThread;

    public static void main(String[] args) {
//        String hashedPassword = Hashing.sha256()
//                .hashString(password, StandardCharsets.UTF_8)
//                .toString();

        // Create runnable task & create new thread to run it on
        CrackerTask crackerTask = new CrackerTask();
        bruteForceThread = Executors.newFixedThreadPool(1);
        bruteForceThread.submit(crackerTask);
    }

    public static void shutdown() {
        bruteForceThread.shutdown();
    }

}