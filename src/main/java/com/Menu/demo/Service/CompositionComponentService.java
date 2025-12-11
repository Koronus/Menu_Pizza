package com.Menu.demo.Service;

import com.Menu.demo.Entity.CompositionComponent;
import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Repository.CompositionComponentRepository;
import com.Menu.demo.Repository.CompositionDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompositionComponentService {

    @Autowired
    private CompositionComponentRepository compositionComponentRepository;

    public List<CompositionComponent> findAll(Integer componentId) {
        return compositionComponentRepository.findByComponentCodeComponent(componentId);
    }

    public List<CompositionComponent> findByComponentCode(Integer componentId) {
        return compositionComponentRepository.findByComponentCode(componentId);

    }

    public void saveCompositionComponent(CompositionComponent compositionComponent) { compositionComponentRepository.save(compositionComponent);}

//    public List<CompositionDish> findById(Integer dishId, Integer componentId) {
//        return compositionDishRepository.findByDishIdAndComponentId(dishId, componentId)
//                .stream()
//                .findFirst();
//    }

    public CompositionComponent findByComponentIdAndMicroelementId(Integer componentId, Integer microelementId) {
        // Используем Optional для безопасной обработки
        return compositionComponentRepository.findByComponentIdAndMicroelementId(componentId,microelementId)
                .orElse(null);
    }

    //Удаление компонента
    @Transactional
    public void deleteRelationMicroelement(Integer componentId, Integer microelementId){compositionComponentRepository.deleteByComponentIdAndMicroelementId(componentId,microelementId);}

}
