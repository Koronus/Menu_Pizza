package com.Menu.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable  // ← Помечаем как встраиваемый класс
public class CompositionComponentId implements Serializable {

    @Column(name = "code_component")
    private Integer codeComponent;      // Первая часть ключа

    @Column(name = "code_microelement")
    private Integer codeMicroelement;   // Вторая часть ключа

    // ОБЯЗАТЕЛЬНО: конструктор по умолчанию
    public CompositionComponentId() {}

    // Конструктор с параметрами
    public CompositionComponentId(Integer codeComponent, Integer codeMicroelement) {
        this.codeComponent = codeComponent;
        this.codeMicroelement = codeMicroelement;
    }

    // ОБЯЗАТЕЛЬНО: геттеры и сеттеры
    public Integer getCodeComponent() {
        return codeComponent;
    }

    public void setCodeComponent(Integer codeComponent) {
        this.codeComponent = codeComponent;
    }

    public Integer getCodeMicroelement() {
        return codeMicroelement;
    }

    public void setCodeMicroelement(Integer codeMicroelement) {
        this.codeMicroelement = codeMicroelement;
    }

    // ОБЯЗАТЕЛЬНО: переопределить equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositionComponentId)) return false;
        CompositionComponentId that = (CompositionComponentId) o;
        return Objects.equals(codeComponent, that.codeComponent) &&
                Objects.equals(codeMicroelement, that.codeMicroelement);
    }

    // ОБЯЗАТЕЛЬНО: переопределить hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(codeComponent, codeMicroelement);
    }

    @Override
    public String toString() {
        return "CompositionComponentID{" +
                "codeComponent=" + codeComponent +
                ", codeMicroelement=" + codeMicroelement +
                '}';
    }
}
