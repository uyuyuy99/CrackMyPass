package edu.uoregon.crackmypass;

import edu.uoregon.crackmypass.menu.PanelStart;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cracker {

    private static ExecutorService crackingThread;
    private static List<String> hashes;
    private static List<String> words;

    // Settings
    private static List<Appendage> appends; // add strings/numbers to end of word
    private static List<Appendage> prepends;// add strings/numbers to beginning of word
    private static boolean capFirst = true; // capitalize first letter
    private static boolean capAll = true; // capitalize all letters
    private static List<Pair<String, String>> replacements; // replaces strings in word

    public static void startCracking() {
        // Create runnable task & create new thread to run it on
        CrackerTask crackerTask = new CrackerTask();
        crackingThread = Executors.newFixedThreadPool(1);
        crackingThread.submit(crackerTask);
    }

    public static void stopCracking() {
        CrackerTask.setRunning(false);
        crackingThread.shutdown();
        PanelStart.onComplete();
    }

    public static List<String> getHashes() {
        if (hashes == null) {
            try {
                Path hashFilePath = Paths.get(ClassLoader.getSystemResource("3938.0.found").toURI());
                hashes = Files.readAllLines(hashFilePath);
                hashes.replaceAll(hash -> hash.substring(0, hash.indexOf(":")));
                Collections.sort(hashes);
            } catch (IOException | NullPointerException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return hashes;
    }

    public static List<String> getWords() {
        if (words == null) {
            try {
                Path dictFilePath = Paths.get(ClassLoader.getSystemResource("words.txt").toURI());
                words = Files.readAllLines(dictFilePath);
            } catch (IOException | NullPointerException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return words;
    }

    public static boolean loadHashes(File file) {
        try {
            hashes = Files.readAllLines(file.toPath());
            if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("found")) {
                hashes.replaceAll(hash -> hash.substring(0, hash.indexOf(":")));
            }
            Collections.sort(hashes);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean loadWords(File file) {
        try {
            words = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Appendage> getAppends() {
        if (appends == null) {
            appends = new ArrayList<>();
            appends.add(new Appendage(0, 100));
            appends.add(new Appendage(1925, 2025));
            appends.add(new Appendage("lover"));
            appends.add(new Appendage("Lover"));
            appends.add(new Appendage("good"));
            appends.add(new Appendage("Good"));
            appends.add(new Appendage("bad"));
            appends.add(new Appendage("Bad"));
        }
        return appends;
    }

    public static void setAppends(String[] lines) {
        appends.clear();
        for (String line : lines) {
            if (line.isEmpty()) continue;
            appends.add(Appendage.fromDisplayText(line));
        }
    }

    public static List<Appendage> getPrepends() {
        if (prepends == null) {
            prepends = new ArrayList<>();
            prepends.add(new Appendage(0, 9));
            prepends.add(new Appendage("ilove"));
            prepends.add(new Appendage("Ilove"));
            prepends.add(new Appendage("ILove"));
        }
        return prepends;
    }

    public static void setPrepends(String[] lines) {
        prepends.clear();
        for (String line : lines) {
            if (line.isEmpty()) continue;
            prepends.add(Appendage.fromDisplayText(line));
        }
    }

    public static boolean getCapFirst() {
        return capFirst;
    }

    public static void setCapFirst(boolean capFirst) {
        Cracker.capFirst = capFirst;
    }

    public static boolean getCapAll() {
        return capAll;
    }

    public static void setCapAll(boolean capAll) {
        Cracker.capAll = capAll;
    }

    public static List<Pair<String, String>> getReplacements() {
        if (replacements == null) {
            replacements = new ArrayList<>();
            replacements.add(Pair.of("s", "$"));
            replacements.add(Pair.of("S", "$"));
            replacements.add(Pair.of("e", "3"));
            replacements.add(Pair.of("E", "3"));
            replacements.add(Pair.of("a", "@"));
            replacements.add(Pair.of("A", "@"));
            replacements.add(Pair.of("o", "0"));
            replacements.add(Pair.of("O", "0"));
            replacements.add(Pair.of("i", "1"));
            replacements.add(Pair.of("I", "1"));
        }
        return replacements;
    }

}
