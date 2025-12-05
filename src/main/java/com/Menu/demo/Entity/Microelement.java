package com.Menu.demo.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "microelements")
public class Microelement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_microelement")
    private Integer codeMicroelement;

    private String title;

    @OneToMany(mappedBy = "microelement")
    private List<CompositionComponent> compositionComponents;

    @OneToOne(mappedBy = "microelement")
    private DailyRecruitment dailyRecruitment;

    public Microelement() {}

    public Microelement(Integer codeMicroelement, String title, List<CompositionComponent> compositionComponents, DailyRecruitment dailyRecruitment) {
        this.codeMicroelement = codeMicroelement;
        this.title = title;
        this.compositionComponents = compositionComponents;
        this.dailyRecruitment = dailyRecruitment;
    }

    public Integer getCodeMicroelement() {
        return codeMicroelement;
    }

    public void setCodeMicroelement(Integer codeMicroelement) {
        this.codeMicroelement = codeMicroelement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CompositionComponent> getCompositionComponents() {
        return compositionComponents;
    }

    public void setCompositionComponents(List<CompositionComponent> compositionComponents) {
        this.compositionComponents = compositionComponents;
    }

    public DailyRecruitment getDailyRecruitment() {
        return dailyRecruitment;
    }

    public void setDailyRecruitment(DailyRecruitment dailyRecruitment) {
        this.dailyRecruitment = dailyRecruitment;
    }

    // Конструкторы, геттеры, сеттеры
}

