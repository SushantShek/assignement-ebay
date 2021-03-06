package com.example.application.view;

import com.example.application.factory.FileRegister;
import com.example.application.business.ReaderInterface;
import com.example.application.domain.CreditInput;
import com.example.application.factory.AbstractFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.exceptions.CsvException;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.util.SharedUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route("")
public class ReportView extends VerticalLayout {

    private Grid<String[]> grid = new Grid<>();

    public ReportView() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".prn", ".csv");
        upload.addSucceededListener(e -> {
            String fileExtn = buffer.getFileName().split("\\.")[1];
            try {
                renderDisplay(buffer.getInputStream(), fileExtn);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        });
        add(upload, grid);
    }

    /**
     * Render the view on Vaadin based on the processed output
     *
     * @param resourceAsStream Input file as Stream
     * @param ext File Extension to find the responsible implementation class
     * @throws JsonProcessingException if an Exception is Thrown
     */
    private void renderDisplay(InputStream resourceAsStream, String ext) throws JsonProcessingException {
        AbstractFactory factory = new FileRegister();//FileRegister.getClass(ext);
        ReaderInterface fileReader = (ReaderInterface) factory.create(ext);

        try {
            List<CreditInput> entries = fileReader.readFile(resourceAsStream);
            List<Field> list = new ArrayList<>(Arrays.asList(CreditInput.class.getDeclaredFields()));
            grid.removeAllColumns();

            for (int i = 0; i < list.size(); i++) {
                int colIndex = i;
                grid.addColumn(row -> row[colIndex])
                        .setHeader(SharedUtil.camelCaseToHumanFriendly(list.get(colIndex).getName()));
            }
            List<String[]> strArr = entries.stream()
                    .map(ci -> new String[]{ci.getName(), ci.getAddress(), ci.getPostCode(),
                    ci.getPhoneNumber(), ci.getCreditLimit(), ci.getBirthDate()}).collect(Collectors.toList());
            grid.setItems(strArr);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}

