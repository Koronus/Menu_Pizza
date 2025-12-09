package com.Menu.demo.Repository;

import com.Menu.demo.Entity.Component;
import com.Menu.demo.Entity.Dishe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {




    // Базовые методы поиска компонентов
    List<Component> findByTitle(String title);

    List<Component> findByTitleContainingIgnoreCase(String title);

    List<Component> findByPriceLessThan(BigDecimal price);

    // Компоненты с фильтрацией по цене
    @Query("SELECT c FROM Component c " +
            "WHERE (:minPrice IS NULL OR c.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR c.price <= :maxPrice) " +
            "ORDER BY c.price DESC")
    List<Component> findByPriceRange(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice);

    // Компоненты с калорийностью выше указанной
    List<Component> findByCalorieGreaterThan(BigDecimal calorie);
}

