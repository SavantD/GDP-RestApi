package com.rootcode.gdprest.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestValidatorUtilTest {

    @Test
    public void validateCorrectParams() {
        String cCode = "LKA";
        int year = 2016;
        boolean actual = RequestValidatorUtil.isValidParams(cCode, year);
        Assertions.assertTrue(actual);
    }

    @Test
    public void validateIncorrectCodeParam() {
        String cCode = "L";
        int year = 2016;
        boolean actual = RequestValidatorUtil.isValidParams(cCode, year);
        Assertions.assertFalse(actual);
    }

    @Test
    public void validateIncorrectYearParam() {
        String cCode = "LKA";
        int year = 1975;
        boolean actual = RequestValidatorUtil.isValidParams(cCode, year);
        Assertions.assertFalse(actual);
    }

}
