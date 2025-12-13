package com.Menu.demo.Dto;


import java.math.BigDecimal;
import java.util.List;



import java.math.BigDecimal;
import java.util.List;

public class NutritionReportDTO {
    private String componentName;
    private BigDecimal calorie;
    private BigDecimal price;
    private List<MicroelementInfoDTO> microelements;
    private BigDecimal totalMicroelements;
    private BigDecimal caloriePerRuble;

    // Конструкторы
    public NutritionReportDTO() {}

    // Геттеры и сеттеры
    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public BigDecimal getCalorie() {
        return calorie;
    }

    public void setCalorie(BigDecimal calorie) {
        this.calorie = calorie;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<MicroelementInfoDTO> getMicroelements() {
        return microelements;
    }

    public void setMicroelements(List<MicroelementInfoDTO> microelements) {
        this.microelements = microelements;
    }

    public BigDecimal getTotalMicroelements() {
        return totalMicroelements;
    }

    public void setTotalMicroelements(BigDecimal totalMicroelements) {
        this.totalMicroelements = totalMicroelements;
    }

    public BigDecimal getCaloriePerRuble() {
        return caloriePerRuble;
    }

    public void setCaloriePerRuble(BigDecimal caloriePerRuble) {
        this.caloriePerRuble = caloriePerRuble;
    }
}
