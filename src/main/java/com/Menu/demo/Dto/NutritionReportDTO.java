package com.Menu.demo.Dto;

import java.math.BigDecimal;
import java.util.List;

// DTO для отчета 3
public class NutritionReportDTO {
    private String componentName;
    private BigDecimal calorie;
    private BigDecimal price;
    private List<MicroelementInfoDTO> microelements;
    private BigDecimal totalMicroelements;
    private BigDecimal caloriePerRuble; // вычисляемое поле

    // вычисляемое поле - уровень питательности
    public String getNutritionLevel() {
        if (calorie == null) return "Нет данных";
        if (calorie.compareTo(new BigDecimal("200")) > 0) return "Высокая";
        if (calorie.compareTo(new BigDecimal("100")) > 0) return "Средняя";
        return "Низкая";
    }

    // геттеры/сеттеры

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
