package com.rootcode.gdprest.service;

import com.rootcode.gdprest.dto.GrowthRateDTO;
import com.rootcode.gdprest.exception.DatasetNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GrowthRateService {

    private static final Logger logger = LoggerFactory.getLogger(GrowthRateService.class);
    public final  String allGDPRatesDatasetPath = "/datasets/All country GDP Dataset - gdp_csv.csv";
    public final  String cCodesDatasetPath = "/datasets/Country code Alpha-2 Alpha-3 conversion dataset - Sheet1.csv";

    private final String DELIMETER = ",";

    /**
     * Extracts the correct GrowthRate for the given Country code & Year.
     * @param cCode
     * @param year
     * @return
     * @throws DatasetNotFoundException
     */
    public GrowthRateDTO getCountryGrowthRate(String cCode, int year) throws DatasetNotFoundException {
        GrowthRateDTO rateDTO = null;
        try {
            if(cCode.length() == 2){
                cCode = getAlpha3CountryCode(cCode);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(allGDPRatesDatasetPath)));
            String line;
            reader.readLine(); // skipping header

            /**
                reading line by line to avoid heap memory overflows.
                Also could have used Apache Commons IO to iterate the lines,
                but sticked with the good old manuall approach. We could also use Spring Batch.
             **/
            while((line = reader.readLine()) != null){
                rateDTO = getGrowthRateDTO(line, cCode, year);
                if(rateDTO != null)
                    break;
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DatasetNotFoundException(ex.getMessage());
        } catch (NullPointerException npEx) {
            logger.error(npEx.getCause().getMessage(), npEx);
            throw new DatasetNotFoundException(npEx.toString());
        }

        return  rateDTO;
    }

    /**
     * Handles the conversio between Alpha 2 => Alpha 3 country codes.
     * @param alpha2Code
     * @return
     * @throws DatasetNotFoundException
     */
    public String getAlpha3CountryCode(String alpha2Code) throws DatasetNotFoundException {
        String alpha3Code = "";
        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(cCodesDatasetPath)));
            String line;
            reader.readLine(); // skipping header
            while((line = reader.readLine()) != null){
                alpha3Code = convertAlpha2ToAlpha3(line, alpha2Code);
                if(alpha3Code != null) {
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DatasetNotFoundException(ex.getMessage());
        } catch (NullPointerException npEx) {
            logger.error(npEx.getCause().getMessage(), npEx);
            throw new DatasetNotFoundException(npEx.toString());
        }

        return  alpha3Code;
    }

    public GrowthRateDTO getGrowthRateDTO(String line, String countryCode, int year) {
        String[] lineArry = line.split(DELIMETER);
        return getRateDTO(lineArry, countryCode, year);
    }

    public String convertAlpha2ToAlpha3(String line, String countryCode) {
        String[] lineArry = line.split(DELIMETER);
        int indexOfAlpha2CodeColumn = getColumnIndexOfAlpha2CodeColumn(lineArry);
        boolean codeExists = lineArry[indexOfAlpha2CodeColumn].equals(countryCode);
        if(codeExists){
            return  lineArry[indexOfAlpha2CodeColumn+1];
        }
        return null;
    }

    public GrowthRateDTO getRateDTO(String[] lineData, String countryCode, int year) {
        GrowthRateDTO rateDto = null;
        int indexOfYear = getColumnIndexOfYear(lineData);
        int lineYear = Integer.parseInt(lineData[indexOfYear]);
        if(lineData[indexOfYear-1].equals(countryCode) &&  lineYear == year){
            rateDto = new GrowthRateDTO();
            rateDto.setCountryCode(countryCode);
            rateDto.setYear(year);
            rateDto.setRate(lineData[indexOfYear+1]);
        }
        return rateDto;
    }

    /**
     * The below function calculate the correct index of the Year column,
     * if in case there are inconsistencies for the delimetered values in our dataset
     * @param lineData
     * @return
     */
    public int getColumnIndexOfYear(String[] lineData) {
        int defaultIndex = 2;
        try {
            Integer.parseInt(lineData[defaultIndex]);
            return defaultIndex;
        }
        catch (Exception ex) {
            return defaultIndex+1;
        }

    }

    /**
     * The below function calculate the correct index of the Alpha-2 code column,
     * if in case there are inconsistencies for the delimetered values in our dataset
     * @param lineData
     * @return
     */
    public int getColumnIndexOfAlpha2CodeColumn(String[] lineData) {
        int defaultIndex = 1;
        if(lineData.length > 4){
            return defaultIndex + 1;
        } else {
            return defaultIndex;
        }
    }
}
