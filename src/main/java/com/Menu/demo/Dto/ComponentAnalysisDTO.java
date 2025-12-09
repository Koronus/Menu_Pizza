package com.Menu.demo.Dto;

import java.math.BigDecimal;
import java.util.List;

// DTO для отчета 2
public class ComponentAnalysisDTO {
    private Integer componentId;
    private String componentName;
    private BigDecimal price;
    private BigDecimal calorie;
    private BigDecimal weight;
    private Long usedInDishes;
    private List<String> dishNames;

    // вычисляемое поле - популярность
    public String getPopularityLevel() {
        if (usedInDishes == null) return "Нет данных";
        if (usedInDishes > 5) return "Высокая";
        if (usedInDishes > 2) return "Средняя";
        return "Низкая";
    }

    // геттеры/сеттеры

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCalorie() {
        return calorie;
    }

    public void setCalorie(BigDecimal calorie) {
        this.calorie = calorie;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getUsedInDishes() {
        return usedInDishes;
    }

    public void setUsedInDishes(Long usedInDishes) {
        this.usedInDishes = usedInDishes;
    }

    public List<String> getDishNames() {
        return dishNames;
    }

    public void setDishNames(List<String> dishNames) {
        this.dishNames = dishNames;
    }
}



    // геттеры/сеттеры

