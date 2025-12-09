package com.Menu.demo.Repository;

import com.Menu.demo.Entity.CompositionComponent;
import com.Menu.demo.Entity.CompositionComponentId;
import com.Menu.demo.Entity.CompositionDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompositionComponentRepository extends JpaRepository<CompositionComponent, Integer> {

    // Найти конкретную запись по обеим частям ключа
//    @Query("SELECT cd FROM CompositionDish cd WHERE cd.dish.dishesID = :dishId AND cd.component.codeComponent = :componentId")
//    List<CompositionDish> findByDishIdAndComponentId(@Param("dishId") Integer dishId,
//                                                     @Param("componentId") Integer componentId);


    List<CompositionComponent> findByComponentCodeComponent(Integer codeComponent);

    // Используем поле из составного ключа
    @Query("SELECT cd FROM CompositionComponent cd WHERE cd.id.componentId = :componentId")
    List<CompositionComponent> findByComponentCode(@Param("componentId") Integer componentId);

}