package edu.uoregon.crackmypass;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import edu.uoregon.crackmypass.menu.PanelOutput;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CrackerTask implements Runnable {

    private List<String> hashes;
    private List<String> words;
    private Map<String, String> cracked = new HashMap<>();

    private static boolean running = false; // Whether the task is currently running or not
    private long attempts = 0; // Current number of attempts we've made
    private long startTime;
    private HashFunction hasher = Hashing.md5();

    public CrackerTask() {
        this.hashes = Cracker.getHashes();
        this.words = Cracker.getWords();
    }

    @Override
    public void run() {
        setRunning(true);
        this.startTime = System.currentTimeMillis();

        for (String word : words) {
            if (!isRunning()) {
                PanelOutput.setTriedAmount(attempts);
                return;
            }
            List<String> passwordTries = generateStrings(word);

            for (String pswd : passwordTries) {
                if (++attempts % 10000 == 0) {
                    PanelOutput.setTriedAmount(attempts);
                }

                // Use binary search to search hashes for O(logn) time
                int index = Collections.binarySearch(hashes, hasher.hashString(pswd, StandardCharsets.UTF_8).toString());

                if (index >= 0) {
                    cracked.put(hashes.get(index), pswd);
                    long totalMs = System.currentTimeMillis() - startTime;
                    System.out.println();
                    System.out.println("Cracked a hash: \"" + pswd + "\" -> " + hashes.get(index));
                    System.out.println("Total of " + cracked.size() + " found in " + totalMs + " ms");
                    PanelOutput.addLine(pswd + " -> " + hashes.get(index));
                    PanelOutput.setFoundAmount(cracked.size());
                }
            }
        }

        PanelOutput.setTriedAmount(attempts);
        long totalMs = System.currentTimeMillis() - startTime;
        System.out.println();
        System.out.println("FINISHED!");
        System.out.println("Found " + cracked.size() + " passwords from " + Util.formatLong(attempts) + " attempts in " + totalMs + " ms!");
        Cracker.stopCracking();
    }

    // Generate a list of possible passwords given a word from the dictionary
    private List<String> generateStrings(String baseWord) {
        List<String> list = new ArrayList<>();
        list.add(baseWord);

        // Swap out (replace) characters/strings in the word
        List<Pair<String, String>> replacements = Cracker.getReplacements();
        if (!replacements.isEmpty()) {
            String word = baseWord;
            for (Pair<String, String> repl : replacements) {
                word = word.replace(repl.getLeft(), repl.getRight());
            }
            if (!word.equals(baseWord)) {
                list.add(word);
            }
        }

        // Add capitalized permutations
        if (Cracker.getCapFirst()) {
            list.add(StringUtils.capitalize(baseWord));
        }
        if (Cracker.getCapAll()) {
            list.add(baseWord.toUpperCase());
        }

        // Add permutations w/ appendages
        if (Cracker.isAppendBoth()) {
            List<Appendage> textAppends = Cracker.getAppends().stream().filter(Appendage::isText).toList();
            List<Appendage> numberAppends = Cracker.getAppends().stream().filter(a -> !a.isText()).toList();

            ListIterator<String> iter = list.listIterator();
            while (iter.hasNext()) {
                String plainWord = iter.next();
                List<String> toAdd = new ArrayList<>();

                for (Appendage append : textAppends) {
                    for (String string : append.getStrings()) {
                        toAdd.add(plainWord + string);
                    }
                }

                ListIterator<String> iter2 = toAdd.listIterator();
                while (iter2.hasNext()) {
                    String word = iter2.next();

                    for (Appendage append : numberAppends) {
                        for (String string : append.getStrings()) {
                            iter2.add(word + string);
                        }
                    }
                }

                for (String add : toAdd) iter.add(add);
            }
        }
        else {
            ListIterator<String> iter = list.listIterator();
            while (iter.hasNext()) {
                String word = iter.next();

                for (Appendage append : Cracker.getAppends()) {
                    for (String string : append.getStrings()) {
                        iter.add(word + string);
                    }
                }
            }
        }

        // Add permutations w/ prependages
        ListIterator<String> iter = list.listIterator();
        while (iter.hasNext()) {
            String word = iter.next();

            for (Appendage prepend : Cracker.getPrepends()) {
                for (String string : prepend.getStrings()) {
                    iter.add(string + word);
                }
            }
        }

        if (!Cracker.getTryOriginalWord()) {
            list.remove(baseWord);
            list.remove(StringUtils.capitalize(baseWord));
            list.remove(baseWord.toUpperCase());
        }

//        System.out.println("Permutations for word '" + baseWord + "':");
//        for (String word : list) {
//            System.out.println("  " + word);
//        }

        return list;
    }

    public static synchronized boolean isRunning() {
        return running;
    }

    public static synchronized void setRunning(boolean newValue) {
        running = newValue;
    }

}
