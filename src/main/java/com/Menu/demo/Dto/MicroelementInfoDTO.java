package com.Menu.demo.Dto;


import java.math.BigDecimal;


public class MicroelementInfoDTO {
    private String microelementName;
    private BigDecimal quantityPer100;

    public String getMicroelementName() {
        return microelementName;
    }

    public void setMicroelementName(String microelementName) {
        this.microelementName = microelementName;
    }

    public BigDecimal getQuantityPer100() {
        return quantityPer100;
    }

    public void setQuantityPer100(BigDecimal quantityPer100) {
        this.quantityPer100 = quantityPer100;
    }
}
