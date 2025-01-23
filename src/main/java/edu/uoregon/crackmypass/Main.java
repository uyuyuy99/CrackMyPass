package edu.uoregon.crackmypass;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static int MIN_LENGTH = 1;
    public static int MAX_LENGTH = 24;
    private static ExecutorService bruteForceThread;

    public static void main(String[] args) {
        String password = args.length == 0 ? "password" : args[0];

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            System.out.println("ERROR: Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");
            System.exit(0);
        }

        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        System.out.println("Chosen password: " + password);
        System.out.println("Password hash (SHA-256): " + hashedPassword);

        // Create runnable task & create new thread to run it on
        CrackerTask crackerTask = new CrackerTask(hashedPassword, MIN_LENGTH, MAX_LENGTH,
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        bruteForceThread = Executors.newFixedThreadPool(1);
        bruteForceThread.submit(crackerTask);
    }

    public static void shutdown() {
        bruteForceThread.shutdown();
    }

}