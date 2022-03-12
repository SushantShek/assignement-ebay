package com.example.application.factory;

import com.example.application.business.ReaderInterface;
import com.example.application.business.reader.CSVFileReader;
import com.example.application.business.reader.PRNFileReader;
import com.example.application.factory.AbstractFactory;

public class FileRegister implements AbstractFactory {

    @Override
    public ReaderInterface create(String type) {
        if ("csv".equalsIgnoreCase(type)) {
            return new CSVFileReader();
        } else if ("prn".equalsIgnoreCase(type)) {
            return new PRNFileReader();
        }
        return null;
    }
}
