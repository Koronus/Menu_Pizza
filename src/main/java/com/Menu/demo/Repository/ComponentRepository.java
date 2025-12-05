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

    List<Component> findByCalorieGreaterThan(BigDecimal calorie);

    List<Component> findByPriceLessThan(BigDecimal price);

//    // Компоненты, которые НЕ добавлены в блюдо (для выпадающего списка)
//    @Query("SELECT c FROM Component c WHERE c.codeComponent NOT IN " +
//            "(SELECT cd.component.codeComponent FROM CompositionDish cd WHERE cd.dish.dishesID = :dishId)")
//    List<Component> findAvailableComponentsForDish(@Param("dishId") Integer dishId);
//
//    // Компоненты, используемые в блюдах определенной категории
//    @Query("SELECT DISTINCT c FROM Component c " +
//            "JOIN CompositionDish cd ON c.codeComponent = cd.component.codeComponent " +
//            "JOIN cd.dish d " +
//            "JOIN d.typeOfDish t " +
//            "WHERE t.codeType = :categoryId")
//    List<Component> findComponentsByCategoryId(@Param("categoryId") Integer categoryId);
}

