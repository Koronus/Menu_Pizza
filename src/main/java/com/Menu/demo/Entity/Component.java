package com.Menu.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "components")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_component")
    private Integer codeComponent;

    private String title;
    private BigDecimal calorie;
    private BigDecimal price;
    private BigDecimal weight;

    @OneToMany(mappedBy = "component")
    private List<CompositionDish> compositionDishes;

    @OneToMany(mappedBy = "component")
    private List<CompositionComponent> compositionComponents;

    public Component(){}

    public Component(Integer codeComponent, String title, BigDecimal calorie, BigDecimal price, BigDecimal weight, List<CompositionDish> compositionDishes, List<CompositionComponent> compositionComponents) {
        this.codeComponent = codeComponent;
        this.title = title;
        this.calorie = calorie;
        this.price = price;
        this.weight = weight;
        this.compositionDishes = compositionDishes;
        this.compositionComponents = compositionComponents;
    }

    public Integer getCodeComponent() {
        return codeComponent;
    }

    public void setCodeComponent(Integer codeComponent) {
        this.codeComponent = codeComponent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<CompositionDish> getCompositionDishes() {
        return compositionDishes;
    }

    public void setCompositionDishes(List<CompositionDish> compositionDishes) {
        this.compositionDishes = compositionDishes;
    }

    public List<CompositionComponent> getCompositionComponents() {
        return compositionComponents;
    }

    public void setCompositionComponents(List<CompositionComponent> compositionComponents) {
        this.compositionComponents = compositionComponents;
    }
}
