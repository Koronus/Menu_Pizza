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

    // Для отчета 1: количество блюд по всем категориям
    @Query("SELECT c.title, COUNT(d), SUM(d.price), AVG(d.price) " +
            "FROM Dishe d JOIN d.typeOfDish c " +
            "GROUP BY c.codeType, c.title")
    List<Object[]> findDishesCountByAllCategories();

    // Для отчета 1: для конкретной категории
    @Query("SELECT c.title, COUNT(d), SUM(d.price), AVG(d.price) " +
            "FROM Dishe d JOIN d.typeOfDish c " +
            "WHERE c.codeType = :categoryId " +
            "GROUP BY c.codeType, c.title")
    List<Object[]> findDishesCountByCategoryId(@Param("categoryId") Integer categoryId);

    // Найти блюда по категории
    List<Dishe> findByTypeOfDish_CodeType(Integer categoryId);



}