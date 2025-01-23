package edu.uoregon.crackmypass;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class CrackerTask implements Runnable {

    private static DecimalFormat formatLong = new DecimalFormat("#,###");

    private boolean running; // Whether the task is currently running or not
    private String targetHash; // The SHA-256 hash of the password we're trying to crack
    private long attempts = 0; // Current number of attempts we've made
    private long startTime;
    private HashFunction hasher = Hashing.sha256();

    private final int minLength;
    private final int maxLength;
    private final char[] validChars; // All the characters that we'll iterate through

    public CrackerTask(String targetHash, int minLength, int maxLength, String validCharString) {
        this.targetHash = targetHash;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.validChars = validCharString.toCharArray();
    }

    @Override
    public void run() {
        this.running = true;
        this.startTime = System.currentTimeMillis();
        char[] current = new char[maxLength];  // Array to build the current string

        // Iterate over all possible lengths from minLength to maxLength
        for (int length = minLength; length <= maxLength; length++) {
            generateStrings(validChars, current, length, 0);
            if (!this.running) return;
        }

        System.out.println("Finished all " + attempts + " attempts.");
        System.out.println("Could not find password with hash: " + targetHash);
        Main.shutdown();
    }

    private void generateStrings(char[] validChars, char[] current, int targetLength, int currentIndex) {
        if (!this.running) return;

        // If current length equals target length, test the current string
        if (currentIndex == targetLength) {
            if (++attempts % 10000000 == 0) {
                System.out.println();
                System.out.println("Current length: " + targetLength);
                System.out.println("Attempts: " + formatLong.format(attempts));
                System.out.println("Seconds elapsed: " + ((System.currentTimeMillis() - startTime) / 1000));
            }
            if (hasher.hashString(new String(current, 0, targetLength), StandardCharsets.UTF_8).toString().equals(targetHash)) {
                long totalMs = System.currentTimeMillis() - startTime;
                System.out.println();
                System.out.println("Found the password with " + formatLong.format(attempts) + " attempts in " + totalMs + " ms!");
                System.out.println("Your password was: " + new String(current, 0, targetLength));
                Main.shutdown();
                this.running = false;
            }
            return;
        }

        // Iterate through each character in validChars and build strings
        for (char c : validChars) {
            current[currentIndex] = c; // Set the character at the current index
            generateStrings(validChars, current, targetLength, currentIndex + 1);
        }
    }

}
