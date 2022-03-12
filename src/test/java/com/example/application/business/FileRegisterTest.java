package com.example.application.business;

import com.example.application.business.reader.CSVFileReader;
import com.example.application.business.reader.PRNFileReader;
import com.example.application.factory.FileRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileRegisterTest {

    FileRegister register;
    @BeforeEach
    void init(){
        register = new FileRegister();
    }

    @Test
    void register() {
        ReaderInterface reader =register.create("csv");
        assertNotNull(reader);
        assertTrue( reader instanceof CSVFileReader);
    }

    @Test
    void testCreatePrn() {
        ReaderInterface reader =register.create("prn");
        assertNotNull(reader);
        assertTrue( reader instanceof PRNFileReader);
    }

    @Test
    void testCreate_Invalid() {
        ReaderInterface reader =register.create("txt");
        assertNull(reader);
    }
}