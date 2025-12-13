package com.Menu.demo.Dto;




import java.math.BigDecimal;



public class CategoryDishesReportDTO {
    private String categoryName;
    private Long dishCount;
    private BigDecimal totalPrice;
    private BigDecimal avgPrice;
    private BigDecimal revenueShare;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getDishCount() {
        return dishCount;
    }

    public void setDishCount(Long dishCount) {
        this.dishCount = dishCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getRevenueShare() {
        return revenueShare;
    }

    public void setRevenueShare(BigDecimal revenueShare) {
        this.revenueShare = revenueShare;
    }
}
