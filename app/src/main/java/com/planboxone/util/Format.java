package com.planboxone.util;

/**
 * Created by Administrator on 2014/7/16.
 */
public class Format {
    Format() {
    }

    public static String formatString(String str) {
        String res = String.format("%2s", str);
        res = res.replaceAll("\\s", "0");
        return res;
    }

    public static String formatString(String weishu, String str) {
        String res = String.format("%" + weishu + "s", str);
        res = res.replaceAll("\\s", "0");
        return res;
    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static String padHour(int c) {
        if (c == 12)
            return String.valueOf(c);
        if (c == 00)
            return String.valueOf(c + 12);
        if (c > 12)
            return String.valueOf(c - 12);
        else
            return String.valueOf(c);
    }

    public static String padAP(int c) {
        if (c == 12)
            return " PM";
        if (c == 00)
            return " AM";
        if (c > 12)
            return " PM";
        else
            return " AM";
    }

}
