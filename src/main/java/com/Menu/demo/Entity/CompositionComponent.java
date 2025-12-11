package com.Menu.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "composition_components")
@IdClass(CompositionComponentId.class)
public class CompositionComponent {


    @Id
    @Column(name = "code_component",insertable = false, updatable = false)
    private Integer componentId;

    @Id
    @Column(name = "code_microelement",insertable = false, updatable = false)
    private Integer microelementId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "code_component",
            referencedColumnName = "code_component",
            insertable = false,  // ← Не вставлять при сохранении
            updatable = false    // ← Не обновлять
    )
    private Component component;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "code_microelement",
            referencedColumnName = "code_microelement",
            insertable = false,  // ← Не вставлять при сохранении
            updatable = false    // ← Не обновлять
    )
    private Microelement microelement;

    @Column(name = "quantity_per_100")
    private BigDecimal quantityPer100;

    public CompositionComponent() {}

    public CompositionComponent( Component component, Microelement microelement, BigDecimal quantityPer100) {

        this.component = component;
        this.microelement = microelement;
        this.quantityPer100 = quantityPer100;
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

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Microelement getMicroelement() {
        return microelement;
    }

    public void setMicroelement(Microelement microelement) {
        this.microelement = microelement;
    }

    public BigDecimal getQuantityPer100() {
        return quantityPer100;
    }

    public void setQuantityPer100(BigDecimal quantityPer100) {
        this.quantityPer100 = quantityPer100;
    }


}

