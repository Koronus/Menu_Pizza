package com.Menu.demo.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "types_of_dishes")
public class TypeOfDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_type")
    private Integer codeType;

    @Column(name = "title_types_of_dishes")
    private String title;

    @OneToMany(mappedBy = "typeOfDish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dishe> dishes = new ArrayList<>();

    public TypeOfDish() {}

    public TypeOfDish(Integer codeType, String title) {
        this.codeType = codeType;
        this.title = title;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

