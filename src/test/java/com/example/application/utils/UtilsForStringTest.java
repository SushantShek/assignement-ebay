package com.example.application.utils;

import com.vaadin.flow.internal.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class UtilsForStringTest {
    UtilsForString utils;

    @Test
    void removeQuotes_withExtraQuotes() {
       String response = utils.removeQuotes("\"Test\"");
       assertEquals("Test",response);
    }

    @Test
    void removeQuotes_withoutExtraQuotes() {
        String response = utils.removeQuotes("Test");
        assertEquals("Test",response);
    }
}