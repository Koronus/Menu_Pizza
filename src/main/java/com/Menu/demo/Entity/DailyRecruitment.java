package com.Menu.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "daily_recruitments")
public class DailyRecruitment {
    @Id
    @Column(name = "code_microelement")
    private Integer codeMicroelement;

    @Column(name = "quantity_in_mg")
    private BigDecimal quantityInMg;

    @OneToOne
    @MapsId
    @JoinColumn(name = "code_microelement")
    private Microelement microelement;

    public DailyRecruitment(Integer codeMicroelement, BigDecimal quantityInMg, Microelement microelement) {
        this.codeMicroelement = codeMicroelement;
        this.quantityInMg = quantityInMg;
        this.microelement = microelement;
    }

    public Integer getCodeMicroelement() {
        return codeMicroelement;
    }

    public void setCodeMicroelement(Integer codeMicroelement) {
        this.codeMicroelement = codeMicroelement;
    }

    public BigDecimal getQuantityInMg() {
        return quantityInMg;
    }

    public void setQuantityInMg(BigDecimal quantityInMg) {
        this.quantityInMg = quantityInMg;
    }

    public Microelement getMicroelement() {
        return microelement;
    }

    public void setMicroelement(Microelement microelement) {
        this.microelement = microelement;
    }
}


