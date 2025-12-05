package com.Menu.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dishes")
public class Dishe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dishesid")
    private Integer dishesId;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_type") // имя столбца в БД
    @JsonIgnore
    private TypeOfDish typeOfDish; // имя поля в Java

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompositionDish> compositionDishes = new ArrayList<>();
    // Конструкторы
    public Dishe() {}

    public Dishe(String title, BigDecimal price, TypeOfDish typeOfDish) {
        this.title = title;
        this.price = price;
        this.typeOfDish = typeOfDish;
    }

    // Геттеры и сеттеры
    public Integer getDishesId() { return dishesId; }
    public void setDishesId(Integer dishesId) { this.dishesId = dishesId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }


    public TypeOfDish getTypeOfDish() { return typeOfDish; }
    public void setTypeOfDish(TypeOfDish typeOfDish) { this.typeOfDish = typeOfDish; }
}

