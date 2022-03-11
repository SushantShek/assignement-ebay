package com.example.application.business.reader;

import com.example.application.business.FileRegister;
import com.example.application.business.ReaderInterface;
import com.example.application.domain.CreditInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PNRFileReader implements ReaderInterface {
    /**
     * Delimiter for PNR file
     */
    private static final String STRING_DELIM = ";";

    /*
      This block registers the filetype to FileRegister
      which helps to decide based on input file which class to process it with.
     */
    static {
        FileRegister.register("prn", new PNRFileReader());
    }

    @Override
    public List<CreditInput> readFile(InputStream tempFile) {
        log.info("calling read file PNR");
        return processPNRInputFile(tempFile).stream().skip(1).map(mapToCredit).collect(Collectors.toList());
    }

    /**
     * Function to validate the data and create mapping
     * for the input to {@link CreditInput} class
     */
    private final Function<String, CreditInput> mapToCredit = (line) -> {
        String[] p = line.split(STRING_DELIM);// PRN has semicolon separated lines
        if (p.length < 6) {
            log.error("PNRFileReader input has incorrect delimiter");
            throw new IllegalArgumentException("Expected mapping parameters are missing");
        }
        CreditInput item = new CreditInput();
        item.setName(p[0]);//<-- this is the first column in the prn file
        item.setAddress(p[1]);
        item.setPostCode(p[2]);
        if (p[3] != null && p[3].trim().length() > 0) {
            item.setPhoneNumber(p[3]);
        }
        if (p[4] != null && p[4].trim().length() > 0) {
            item.setCreditLimit(p[4]);
        }
        if (p[5] != null && p[5].trim().length() > 0) {
            item.setBirthDate(p[5]);
        }
        //more initialization goes here

        return item;
    };

    /**
     * Function to read the input stream coming from file upload
     * Parse it line by line and apply custom processing based of file
     * and data type
     *
     * @param inputFilePath {@link InputStream} file IO
     * @return List of String
     */
    private List<String> processPNRInputFile(InputStream inputFilePath) {
        List<String> prnList = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(inputFilePath, Charset.forName("ISO-8859-16")))) {
            String line;
            StringBuilder sb;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) {
                    continue;
                }
                sb = new StringBuilder();
                // Method called with supplied file data line and the widths of
                // each column as outlined within the file.
                String[] parts = splitStringToChunks(line, 16, 22, 9, 14, 13, 8);

                for (String str : parts) {
                    sb.append(sb.toString().equals("") ? str : "; " + str);
                }
                prnList.add(sb.toString());
            }
        } catch (IOException ex) {
            log.error("Exception in processPNRInputFile ", ex);
        }
        return prnList;
    }

    /**
     * Creating chunks of input line String based on the size of
     * space between the strings
     *
     * @param inputString String line input
     * @param chunkSizes  size of spaces between lines
     * @return Array of String
     */
    private static String[] splitStringToChunks(String inputString, int... chunkSizes) {
        List<String> list = new ArrayList<>();
        try {
            int chunkStart, chunkEnd = 0;
            for (int length : chunkSizes) {
                chunkStart = chunkEnd;
                chunkEnd = chunkStart + length;
                String dataChunk = inputString.substring(chunkStart, chunkEnd);
                list.add(dataChunk.trim());
            }
        } catch (StringIndexOutOfBoundsException ex) {
            log.error("Format of input message is not as expected");
            throw new IllegalArgumentException("Expected mapping parameters are missing");
        }
        return list.toArray(new String[0]);
    }
}