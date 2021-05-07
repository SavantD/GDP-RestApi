package com.rootcode.gdprest.dto;

import java.util.Date;

public class ResponseDTO {

    public String growthRate;
    public Date timestamp;

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
