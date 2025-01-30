package edu.uoregon.crackmypass;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class CrackerTask implements Runnable {

    private static DecimalFormat formatLong = new DecimalFormat("#,###");

    private List<String> hashes;
    private List<String> words;
    private Map<String, String> cracked = new HashMap<>();

//    private boolean running; // Whether the task is currently running or not
    private long attempts = 0; // Current number of attempts we've made
    private long startTime;
    private HashFunction hasher = Hashing.md5();

    public CrackerTask() {
        try {
            Path hashFilePath = Paths.get(ClassLoader.getSystemResource("hashes.txt").toURI());
            hashes = Files.readAllLines(hashFilePath);
            Collections.sort(hashes);

            Path dictFilePath = Paths.get(ClassLoader.getSystemResource("dictionary.txt").toURI());
            words = Files.readAllLines(dictFilePath);
        } catch (IOException | NullPointerException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.startTime = System.currentTimeMillis();

        for (String word : words) {
            List<String> passwordTries = generateStrings(word);

            for (String pswd : passwordTries) {
                ++attempts;
                // Use binary search to search hashes for O(logn) time
                int index = Collections.binarySearch(hashes, hasher.hashString(pswd, StandardCharsets.UTF_8).toString());

                if (index >= 0) {
                    cracked.put(hashes.get(index), pswd);
                    long totalMs = System.currentTimeMillis() - startTime;
                    System.out.println();
                    System.out.println("Cracked a hash: \"" + pswd + "\" -> " + hashes.get(index));
                    System.out.println("Total of " + cracked.size() + " found in " + totalMs + " ms");
                }
            }
        }

        long totalMs = System.currentTimeMillis() - startTime;
        System.out.println();
        System.out.println("FINISHED!");
        System.out.println("Found " + cracked.size() + " passwords from " + formatLong.format(attempts) + " attempts in " + totalMs + " ms!");
        Main.shutdown();
    }

    // Generate a list of possible passwords given a word from the dictionary
    private List<String> generateStrings(String word) {
        // We will make this more complex later -- for right now, it's a simple dictionary attack
        List<String> list = new ArrayList<>();
        list.add(word);
        list.add(StringUtils.capitalize(word));
        list.add(word.toUpperCase());
        return list;
    }

}
