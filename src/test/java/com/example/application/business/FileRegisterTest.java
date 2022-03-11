package com.example.application.business;

import com.example.application.business.reader.CSVFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileRegisterTest {

    @AfterEach
    void tearDown() {
        FileRegister.reg.clear();
    }

    @Test
    void register() {
        FileRegister.register("csv", new CSVFileReader() {
        });
        assertNotNull(FileRegister.reg);
        assertEquals(1, FileRegister.reg.size());
    }

    @Test
    void testGetClass() {
        FileRegister.register("csv", new CSVFileReader() {
        });
        ReaderInterface csvObj = FileRegister.getClass("csv");
        assertNotNull(csvObj);
        assertTrue(csvObj instanceof CSVFileReader);
    }
}