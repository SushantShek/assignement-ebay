package com.example.application.business.reader;

import com.example.application.domain.CreditInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.openqa.selenium.InvalidArgumentException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class CSVFileReaderTest {

    CSVFileReader reader;

    InputStream targetStream;
    String initialString = "Name,Address,Postcode,Phone,Credit Limit,Birthday\n" +
            "Johnson, John,Voorstraat 32,3122gg,020 3849381,10000,01/01/1987";
    String invalidString = "Name,Address,Postcode,Phone,Credit Limit,Birthday\n" +
            "Johnson, John;Voorstraat 32;3122gg;020 3849381;10000;01/01/1987";
    @BeforeEach
    void setUp() {
        reader = new CSVFileReader();
         targetStream = new ByteArrayInputStream(initialString.getBytes());
    }

    @Test
    void readFile(){
        List<CreditInput> output = reader.readFile(targetStream);
        assertNotNull(output);
        assertTrue(output.size() == 1);
        assertEquals("Johnson, John", output.get(0).getName());
    }

    @Test
    void readFile_invalidInput(){
        targetStream = new ByteArrayInputStream(invalidString.getBytes());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> reader.readFile(targetStream));

        String expectedMessage = "Expected mapping parameters are missing";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}