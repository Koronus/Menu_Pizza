package com.Menu.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

public class CompositionComponentId implements Serializable {

    //@Column(name = "code_component")
    private Integer componentId;      // Первая часть ключа

   // @Column(name = "code_microelement")
    private Integer microelementId;   // Вторая часть ключа

    // ОБЯЗАТЕЛЬНО: конструктор по умолчанию
    public CompositionComponentId() {}

    // Конструктор с параметрами

    public CompositionComponentId(Integer componentId, Integer microelementId) {
        this.componentId = componentId;
        this.microelementId = microelementId;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getMicroelementId() {
        return microelementId;
    }

    public void setMicroelementId(Integer microelementId) {
        this.microelementId = microelementId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompositionComponentId that = (CompositionComponentId) o;
        return Objects.equals(componentId, that.componentId) && Objects.equals(microelementId, that.microelementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentId, microelementId);
    }
}
