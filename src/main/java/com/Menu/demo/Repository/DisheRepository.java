package com.Menu.demo.Repository;

import com.Menu.demo.Entity.Dishe;
import com.Menu.demo.Entity.TypeOfDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DisheRepository extends JpaRepository<Dishe, Integer> {
    @Query("SELECT d FROM Dishe d WHERE d.typeOfDish.codeType = :categoryId")
    List<Dishe> findByCategory(@Param("categoryId") Integer categoryId);

    // 1. Отчет: Блюда по категориям - ВСЕ КАТЕГОРИИ
    @Query(value = """
        SELECT 
            tod.title_types_of_dishes AS categoryName,
            COUNT(d.dishesid) AS dishCount,
            COALESCE(SUM(d.price), 0) AS totalPrice,
            COALESCE(AVG(d.price), 0) AS avgPrice
        FROM types_of_dishes tod
        LEFT JOIN dishes d ON tod.code_type = d.code_type
        GROUP BY tod.code_type, tod.title_types_of_dishes
        ORDER BY tod.title_types_of_dishes
        """, nativeQuery = true)
    List<Object[]> findDishesCountByAllCategories();

    // 2. Отчет: Блюда по категориям - КОНКРЕТНАЯ КАТЕГОРИЯ
    @Query(value = """
        SELECT 
            tod.title_types_of_dishes AS categoryName,
            COUNT(d.dishesid) AS dishCount,
            COALESCE(SUM(d.price), 0) AS totalPrice,
            COALESCE(AVG(d.price), 0) AS avgPrice
        FROM types_of_dishes tod
        LEFT JOIN dishes d ON tod.code_type = d.code_type
        WHERE tod.code_type = :categoryId
        GROUP BY tod.code_type, tod.title_types_of_dishes
        """, nativeQuery = true)
    List<Object[]> findDishesCountByCategoryId(@Param("categoryId") Integer categoryId);

    // Найти блюда по категории
    List<Dishe> findByTypeOfDish_CodeType(Integer categoryId);



}