package com.example.application.business;

import com.example.application.domain.CreditInput;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ReaderInterface {
    List<CreditInput> readFile(InputStream tempFile) throws IOException;
}
