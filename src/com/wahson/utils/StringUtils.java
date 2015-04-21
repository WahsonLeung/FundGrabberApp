package com.wahson.utils;

/**
 * Created by wahsonleung on 15/4/13.
 */
public class StringUtils {

    public static String firstLetter2UpperCase(String source) {
        StringBuffer result = new StringBuffer();
        if (source.length() > 0) {
            String firstLetter = source.substring(0, 1).toUpperCase();
            result.append(firstLetter);
        }

        if (source.length() > 1) {
            String leave = source.substring(1);
            result.append(leave);
        }

        return result.toString();
    }
}