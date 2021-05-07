package com.rootcode.gdprest.service;

import com.rootcode.gdprest.dto.GrowthRateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GrowthRateServiceTest {

    @Autowired
    GrowthRateService growthRateService;

    @Test
    public void testGrowthRateDTOForCSVLine() {
        String line = "Sri Lanka,LKA,2015,80611989527";
        GrowthRateDTO expectedDTO = new GrowthRateDTO("LKA", 2015, "80611989527");

        GrowthRateDTO actualDTO = growthRateService.getGrowthRateDTO(line, "LKA", 2015);
        Assertions.assertEquals(expectedDTO.getRate(), actualDTO.getRate());
    }

    @Test
    public void testGrowthRateDTOForUnmatchingCSVLine() {
        String line = "Afghanistan,AFG,2015,80611989527";
        GrowthRateDTO resultDTO = growthRateService.getGrowthRateDTO(line, "LKA", 2015);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void testConvertAlpha2ToAlpha3ForAvailableCode() {

        String line = "Serbia,RS,SRB,688";
        String lineWithIncorrectDelimeters = "Serbia, repulic,RS,SRB,688";
        String cCode = "RS";
        String expected = "SRB";
        String actual = growthRateService.convertAlpha2ToAlpha3(line, cCode);

        Assertions.assertEquals(expected, actual);

        String actual2 = growthRateService.convertAlpha2ToAlpha3(lineWithIncorrectDelimeters, cCode);
    }

    @Test
    public void testConvertAlpha2ToAlpha3ForUnAvailableCode() {

        String line = "Serbia,RS,SRB,688";
        String cCode = "RS";
        String expected = "UNK";
        String actual = growthRateService.convertAlpha2ToAlpha3(line, cCode);

        Assertions.assertNotEquals(expected, actual);
    }
}
