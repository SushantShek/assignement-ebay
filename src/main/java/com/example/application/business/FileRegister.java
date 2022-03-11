package com.example.application.business;

import java.util.HashMap;
import java.util.Map;

public class FileRegister {

    /**
     * This collection stores all the file types the application supports
     * mapped to their Reader Class interface.
     */
    static Map<String, ReaderInterface> reg = new HashMap<>();

    public static void register(String ext, ReaderInterface fr) {
        reg.put(ext, fr);
    }

    public static ReaderInterface getClass(String ext) {
        return reg.get(ext);
    }
}
