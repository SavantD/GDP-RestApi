package com.rootcode.gdprest.controller;

import com.rootcode.gdprest.dto.GrowthRateDTO;
import com.rootcode.gdprest.dto.ResponseDTO;
import com.rootcode.gdprest.exception.DatasetNotFoundException;
import com.rootcode.gdprest.exception.InvalidParamException;
import com.rootcode.gdprest.exception.ResourceNotFoundException;
import com.rootcode.gdprest.service.GrowthRateService;
import com.rootcode.gdprest.util.RequestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping(value = "/gdp/rates")
public class GdpRateController {

    @Autowired
    GrowthRateService rateService;

    private static final Logger logger = LoggerFactory.getLogger(GdpRateController.class);

    @GetMapping(value = "/countryGrowth")
    public ResponseDTO getCountryGrowthRate(@RequestParam(value = "cCode", required = true) String countryCode,
                                            @RequestParam(value = "year", required = true) int year) throws InvalidParamException, ResourceNotFoundException, DatasetNotFoundException {

        ResponseDTO responseDTO = new ResponseDTO();
        if(RequestValidatorUtil.isValidParams(countryCode, year)){
            GrowthRateDTO resultedDto = rateService.getCountryGrowthRate(countryCode, year);
            if(resultedDto != null) {
                responseDTO.setGrowthRate(resultedDto.getRate());
                responseDTO.setTimestamp(Calendar.getInstance().getTime());
            } else {
                ResourceNotFoundException rnfEx = new ResourceNotFoundException("No results found");
                logger.error(rnfEx.getMessage(), rnfEx);
                throw new ResourceNotFoundException(rnfEx.getMessage());
            }
        } else {
            InvalidParamException ex = new InvalidParamException("Incorrect Request parameters. Please Check.");
            logger.info(ex.getMessage());
            throw ex;
        }

        return responseDTO;
    }

}
