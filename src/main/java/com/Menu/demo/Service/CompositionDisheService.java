package com.Menu.demo.Service;

import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Repository.CompositionDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CompositionDish findByDishIdAndComponentId(Integer dishId, Integer componentId) {
        // Используем Optional для безопасной обработки
        return compositionDishRepository.findByDishIdAndComponentId(dishId, componentId)
                .orElse(null);
    }


    public void saveCompositionDishe(CompositionDish compositionDish) { compositionDishRepository.save(compositionDish);}

//    public List<CompositionDish> findById(Integer dishId, Integer componentId) {
//        return compositionDishRepository.findByDishIdAndComponentId(dishId, componentId)
//                .stream()
//                .findFirst();
//    }

    //Удаление компонента
    @Transactional
    public void deleteRelationComponent(Integer dishId, Integer componentId){compositionDishRepository.deleteByDishIdAndComponentId(dishId,componentId);}

}

