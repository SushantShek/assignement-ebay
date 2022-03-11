package com.example.application.business;

import com.example.application.domain.CreditInput;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Reader Interface is a abstraction on top of all the reader
 * class which are supported by this application
 */
public interface ReaderInterface {
    /**
     * Only readFile method is exposed for all file type.
     * This is where actuall business logic is called
     * @param tempFile {@link InputStream} for the uploaded file
     * @return List of {@link CreditInput} class object
     * @throws IOException is thrown in case of Exception
     */
    List<CreditInput> readFile(InputStream tempFile) throws IOException, CsvException;
}
