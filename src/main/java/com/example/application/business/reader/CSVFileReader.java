package com.example.application.business.reader;

import com.example.application.business.FileRegister;
import com.example.application.business.ReaderInterface;
import com.example.application.domain.CreditInput;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class CSVFileReader implements ReaderInterface {

    private static final String DILIM_CSV = ",";

    static {
        FileRegister.register("csv", new CSVFileReader());
    }

    private final Function<String, CreditInput> mapToItem = (line) -> {

        String[] p = line.split(DILIM_CSV);// a CSV has comma separated lines

        CreditInput input = new CreditInput();

        input.setName(p[0] + "," + p[1]);//<-- this is the first column in the csv file
        input.setAddress(p[2]);
        input.setPostCode(p[3]);
        if (p[4] != null && p[4].trim().length() > 0) {
            input.setPhoneNumber(p[4]);
        }
        if (p[5] != null && p[5].trim().length() > 0) {
            input.setCreditLimit(p[5]);
        }
        if (p[6] != null && p[6].trim().length() > 0) {
            input.setBirthDate(p[6]);
        }
        //more initialization goes here

        return input;
    };

    @Override
    public List<CreditInput> readFile(InputStream tempFile) throws JsonProcessingException {
        return processCSVInputFile(tempFile);
    }

    private List<CreditInput> processCSVInputFile(InputStream inputFilePath) {
        List<CreditInput> inputList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputFilePath))) {
            // skip the header of the csv
            return br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("File handling exception CSVFileReader", e);
        }
//        System.out.println(MAPPER.writeValueAsString(inputList));
        return inputList;
    }
}
