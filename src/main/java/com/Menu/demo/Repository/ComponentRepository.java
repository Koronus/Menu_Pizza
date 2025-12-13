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

    // 2. Отчет: Анализ компонентов
    @Query(value = """
        SELECT 
            c.code_component AS componentId,
            c.title AS componentName,
            c.price,
            c.calorie,
            c.weight,
            COUNT(DISTINCT cd.dishesid) AS usedInDishes,
            ARRAY_AGG(DISTINCT d.title) AS dishNames
        FROM components c
        LEFT JOIN composition_dishes cd ON c.code_component = cd.code_component  
        LEFT JOIN dishes d ON cd.dishesid = d.dishesid
        WHERE (:minPrice IS NULL OR c.price >= :minPrice)
          AND (:maxPrice IS NULL OR c.price <= :maxPrice)
        GROUP BY c.code_component, c.title, c.price, c.calorie, c.weight
        ORDER BY COUNT(DISTINCT cd.dishesid) DESC
        """, nativeQuery = true)
    List<Object[]> findComponentsAnalysis(@Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice);

    // 3. Отчет: Анализ питательности
    @Query(value = """
        SELECT 
            c.title AS componentName,
            c.calorie,
            c.price,
            m.title AS microelementName,
            cc.quantity_per_100 AS quantityPer100
        FROM components c
        LEFT JOIN composition_components cc ON c.code_component = cc.code_component  
        LEFT JOIN microelements m ON cc.code_microelement = m.code_microelement
        ORDER BY c.title, cc.quantity_per_100 DESC
        """, nativeQuery = true)
    List<Object[]> findNutritionReportData();
}

