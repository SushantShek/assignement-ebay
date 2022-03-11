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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PNRFileReader implements ReaderInterface {
    private static final String STRING_DELIM = ";";
    static {
        FileRegister.register("prn", new PNRFileReader());
    }

    @Override
    public List<CreditInput> readFile(InputStream tempFile) throws IOException {
        return processPNRInputFile(tempFile).stream().skip(1).map(mapToCredit).collect(Collectors.toList());
    }

    private final Function<String, CreditInput> mapToCredit = (line) -> {

        String[] p = line.split(STRING_DELIM);// a CSV has comma separated lines
        CreditInput item = new CreditInput();

        item.setName(p[0]);//<-- this is the first column in the csv file
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

    private List<String> processPNRInputFile(InputStream inputFilePath) {
        List<String> prnList = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(inputFilePath))) {
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
            log.error("Exceprion in processPNRInputFile ", ex);
        }
        return prnList;
    }

    private static String[] splitStringToChunks(String inputString, int... chunkSizes) {
        List<String> list = new ArrayList<>();
        int chunkStart, chunkEnd = 0;
        for (int length : chunkSizes) {
            chunkStart = chunkEnd;
            chunkEnd = chunkStart + length;
            String dataChunk = inputString.substring(chunkStart, chunkEnd);
            list.add(dataChunk.trim());
        }
        return list.toArray(new String[0]);
    }
}