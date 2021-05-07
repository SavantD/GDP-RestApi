package com.rootcode.gdprest.util;

public class RequestValidatorUtil {

    /**
     * Validates the request params.
     * Checks if the country code is valid and the year is values ranging from 2007-2016
     * @param cCode
     * @param year
     * @return
     */
    public static boolean isValidParams(String cCode, int year) {
        boolean isValid = false;

        if(isValidCountryCode(cCode) && isValidYear(year)){
            isValid = true;
        }

        return isValid;
    }

    private static boolean isValidCountryCode(String cCode) {
        boolean isValid = false;
        int ccodeCharLength = cCode.length();
        if(ccodeCharLength > 1 && ccodeCharLength < 4) {
            isValid = true;
        }

        return  isValid;
    }

    private static boolean isValidYear(int year) {
        boolean isValid = false;
        if(year > 2006 && year < 2017) {
            isValid = true;
        }

        return  isValid;
    }
}
