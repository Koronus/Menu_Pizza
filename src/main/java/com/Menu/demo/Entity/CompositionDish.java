package com.Menu.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "composition_dishes")
@IdClass(CompositionDishId.class)
public class CompositionDish {

    @Id
    @Column(name = "dishesID", insertable = false, updatable = false)
    private Integer dishId;  // ← Простое поле, не связь!

    @Id
    @Column(name = "code_component", insertable = false, updatable = false)
    private Integer componentId;  // ← Простое поле, не связь!

    @ManyToOne
    @JoinColumn(name = "dishesID", referencedColumnName = "dishesId")
    private Dishe dish;  // ← Связь для получения объекта Dish

    @ManyToOne
    @JoinColumn(name = "code_component", referencedColumnName = "code_component")
    private Component component;  // ← Связь для получения объекта Component

    @Column(name = "quantity")
    private Integer quantity;

    // Конструкторы
    public CompositionDish() {
    }

    public CompositionDish(Integer dishId, Integer componentId, Dishe dish, Component component, Integer quantity) {
        this.dishId = dishId;
        this.componentId = componentId;
        this.dish = dish;
        this.component = component;
        this.quantity = quantity;
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

    public Dishe getDish() {
        return dish;
    }

    public void setDish(Dishe dish) {
        this.dish = dish;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}