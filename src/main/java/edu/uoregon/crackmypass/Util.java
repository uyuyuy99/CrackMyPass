package edu.uoregon.crackmypass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    private static DecimalFormat formatLong = new DecimalFormat("#,###");

    public static String formatLong(long val) {
        return formatLong.format(val);
    }

    public static List<String> readAllLines(String resourceName) {
        InputStream stream = Main.class.getResourceAsStream("/" + resourceName);
        InputStreamReader inputStreamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        return reader.lines().collect(Collectors.toList());
    }

}
