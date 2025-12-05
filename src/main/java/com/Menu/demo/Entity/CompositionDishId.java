package com.Menu.demo.Entity;

import java.io.Serializable;
import java.util.Objects;

public class CompositionDishId implements Serializable {

    private Integer dishId;      // должно совпадать с именем поля в CompositionDish
    private Integer componentId; // должно совпадать с именем поля в CompositionDish

    public CompositionDishId() {}

    public CompositionDishId(Integer dish, Integer component) {
        this.dishId = dish;
        this.componentId = component;
    }

    // Геттеры и сеттеры
    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositionDishId)) return false;
        CompositionDishId that = (CompositionDishId) o;
        return Objects.equals(dishId, that.dishId) &&
                Objects.equals(componentId, that.componentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishId, componentId);
    }
}