package edu.uoregon.crackmypass;

import java.text.DecimalFormat;

public class Util {

    private static DecimalFormat formatLong = new DecimalFormat("#,###");

    public static String formatLong(long val) {
        return formatLong.format(val);
    }

}
