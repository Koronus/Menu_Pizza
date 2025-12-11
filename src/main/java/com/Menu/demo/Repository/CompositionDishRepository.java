package com.Menu.demo.Repository;

import com.Menu.demo.Entity.CompositionDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompositionDishRepository extends JpaRepository<CompositionDish, Integer> {
    // Найти конкретную запись по обеим частям ключа
//    @Query("SELECT cd FROM CompositionDish cd WHERE cd.dish.dishesID = :dishId AND cd.component.codeComponent = :componentId")
//    List<CompositionDish> findByDishIdAndComponentId(@Param("dishId") Integer dishId,
//                                                     @Param("componentId") Integer componentId);

    List<CompositionDish> findByDishDishesId(Integer dishId);

    //  Используем поле из составного ключа
    @Query("SELECT cd FROM CompositionDish cd WHERE cd.id.dishId = :dishId")
    List<CompositionDish> findByDishId(@Param("dishId") Integer dishId);

    // Используя составной ключ напрямую
    @Query("SELECT cd FROM CompositionDish cd WHERE cd.id.dishId = :dishId AND cd.id.componentId = :componentId")
    Optional<CompositionDish> findByDishIdAndComponentId(@Param("dishId") Integer dishId,
                                                         @Param("componentId") Integer componentId);

    // Удаление
    void deleteByDishIdAndComponentId(Integer dishId, Integer componentId);

//    // Вариант 2: Если хотите по связанной сущности, убедитесь что правильно названо поле
//    @Query("SELECT cd FROM CompositionDish cd WHERE cd.dish.id = :dishId")
//    List<CompositionDish> findByDishId2(@Param("dishId") Integer dishId);

}
