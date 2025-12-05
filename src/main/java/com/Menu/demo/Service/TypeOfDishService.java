package com.Menu.demo.Service;

import com.Menu.demo.Entity.TypeOfDish;
import com.Menu.demo.Repository.TypeOfDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeOfDishService {
    @Autowired
    private TypeOfDishRepository typeOfDishRepository;

    public List<TypeOfDish> findAll() {
        return typeOfDishRepository.findByType();
    }

    public TypeOfDish getTypeOfDishById(Integer id){
        return typeOfDishRepository.findById(id).orElse(null);
    }

    public void saveTypeOfDish(TypeOfDish typeOfDish)
    {
        typeOfDishRepository.save(typeOfDish);
    }

    public void deleteTypeOfDish(Integer id)
    {
        typeOfDishRepository.deleteById(id);
    }


}
