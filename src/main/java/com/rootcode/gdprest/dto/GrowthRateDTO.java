package com.rootcode.gdprest.dto;

public class GrowthRateDTO {

    private String countryCode;
    private int year;
    private String rate;

    public GrowthRateDTO() {}

    public  GrowthRateDTO(String countryCode, int year, String rate) {
        this.countryCode = countryCode;
        this.year = year;
        this.rate = rate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
