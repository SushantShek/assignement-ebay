package com.example.application.utils;

public class UtilsForString {

    private static final String REGEX = "^\"|\"$";
    /**
     * Removes extra added quotes from a String
     * @param value String to be cleaned
     * @return cleaned String
     */
    public static String removeQuotes(String value){
        return value.replaceAll(REGEX, "");
    }
}
