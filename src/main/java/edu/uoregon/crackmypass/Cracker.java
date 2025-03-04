package edu.uoregon.crackmypass;

import edu.uoregon.crackmypass.menu.PanelStart;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Cracker {

    private static ExecutorService crackingThread;
    private static List<String> hashes;
    private static List<String> words;

    // Settings
    private static List<Appendage> appends; // add strings/numbers to end of word
    private static boolean appendBoth = false; // append text & numbers separately
    private static List<Appendage> prepends;// add strings/numbers to beginning of word
    private static boolean capFirst = true; // capitalize first letter
    private static boolean capAll = true; // capitalize all letters
    private static List<Pair<String, String>> replacements; // replaces strings in word
    private static boolean tryOriginalWord = true; // whether to try the original word or not

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
            hashes = Util.readAllLines("3938.0.found");
            hashes.replaceAll(hash -> hash.substring(0, hash.indexOf(":")));
            Collections.sort(hashes);
        }
        return hashes;
    }

    public static List<String> getWords() {
        if (words == null) {
            words = Util.readAllLines("words.txt");
            words.removeIf(String::isEmpty);
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

    public static synchronized List<Appendage> getAppends() {
        if (appends == null) {
            appends = new ArrayList<>();
            appends.add(new Appendage(0, 100));
            appends.add(new Appendage(1925, 2025));
            appends.add(new Appendage("Guy"));
            appends.add(new Appendage("guy"));
            appends.add(new Appendage("Girl"));
            appends.add(new Appendage("girl"));
            appends.add(new Appendage("Man"));
            appends.add(new Appendage("man"));
            appends.add(new Appendage("!"));
        }
        return appends;
    }

    public static synchronized void setAppends(String[] lines) {
        appends.clear();
        for (String line : lines) {
            if (line.isEmpty()) continue;
            appends.add(Appendage.fromDisplayText(line));
        }
    }

    public static synchronized boolean isAppendBoth() {
        return appendBoth;
    }

    public static synchronized void setAppendBoth(boolean appendBoth) {
        Cracker.appendBoth = appendBoth;
    }

    public static synchronized List<Appendage> getPrepends() {
        if (prepends == null) {
            prepends = new ArrayList<>();
            prepends.add(new Appendage(0, 9));
            prepends.add(new Appendage("ilove"));
            prepends.add(new Appendage("Ilove"));
            prepends.add(new Appendage("Cool"));
            prepends.add(new Appendage("cool"));
            prepends.add(new Appendage("Bad"));
            prepends.add(new Appendage("bad"));
            prepends.add(new Appendage("Good"));
            prepends.add(new Appendage("good"));
        }
        return prepends;
    }

    public static synchronized void setPrepends(String[] lines) {
        prepends.clear();
        for (String line : lines) {
            if (line.isEmpty()) continue;
            prepends.add(Appendage.fromDisplayText(line));
        }
    }

    public static synchronized boolean getCapFirst() {
        return capFirst;
    }

    public static synchronized void setCapFirst(boolean capFirst) {
        Cracker.capFirst = capFirst;
    }

    public static synchronized boolean getCapAll() {
        return capAll;
    }

    public static synchronized void setCapAll(boolean capAll) {
        Cracker.capAll = capAll;
    }

    public static synchronized List<Pair<String, String>> getReplacements() {
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

    public static synchronized void setReplacements(List<Pair<String, String>> replacements) {
        replacements.removeIf(pair -> pair.getLeft().isEmpty());
        Cracker.replacements = replacements;
    }

    public static synchronized boolean getTryOriginalWord() {
        return tryOriginalWord;
    }

    public static synchronized void setTryOriginalWord(boolean newValue) {
        tryOriginalWord = newValue;
    }

    //    public static void setReplacements(Object[][] data) {
//        replacements.clear();
//        for (Object[] row : data) {
//            replacements.add(Pair.of((String) row[0], (String) row[1]));
//        }
//    }

}
