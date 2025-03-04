package edu.uoregon.crackmypass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Appendage {

    private List<String> strings;
    private long min, max;
    private boolean isText;

    private static final Pattern regex = Pattern.compile("^\\s*([0-9]+)\\s*-\\s*([0-9]+)\\s*$");

    // Appending/prepending a single string of text to the password
    public Appendage(String text) {
        this.isText = true;
        this.strings = Collections.singletonList(text);
    }

    // Appending/prepending a series of numbers to the password
    public Appendage(long min, long max) {
        this.isText = false;
        this.min = min;
        this.max = max;
        strings = new ArrayList<>();

        for (long i = min; i <= max; i++) {
            strings.add(String.valueOf(i));
        }
    }

    public List<String> getStrings() {
        return strings;
    }

    public String getDisplayText() {
        if (strings.size() == 1) {
            return strings.get(0);
        } else {
            return min + " - " + max;
        }
    }

    public static Appendage fromDisplayText(String displayText) {
        Matcher matcher = regex.matcher(displayText);

        if (matcher.find()) {
            long min = Long.parseLong(matcher.group(1));
            long max = Long.parseLong(matcher.group(2));
            return new Appendage(min, max);
        } else {
            return new Appendage(displayText);
        }
    }

    public boolean isText() {
        return isText;
    }

}
