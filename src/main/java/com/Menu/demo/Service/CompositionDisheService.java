package com.Menu.demo.Service;

import com.Menu.demo.Entity.Component;
import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Repository.CompositionDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositionDisheService {
    @Autowired
    private CompositionDishRepository compositionDishRepository;

    public List<CompositionDish> findAll(Integer dishId) {
        return compositionDishRepository.findByDishId(dishId);
    }

    public List<CompositionDish> findByDishId(Integer dishId) {
        // Вариант 1: Просто делегируем репозиторию
        return compositionDishRepository.findByDishDishesId(dishId);

        // Или вариант 2, если используете @Query:
        // return compositionDishRepository.findByDishId(dishId);
    }


    public void saveCompositionDishe(CompositionDish compositionDish) { compositionDishRepository.save(compositionDish);}

//    public List<CompositionDish> findById(Integer dishId, Integer componentId) {
//        return compositionDishRepository.findByDishIdAndComponentId(dishId, componentId)
//                .stream()
//                .findFirst();
//    }
}

