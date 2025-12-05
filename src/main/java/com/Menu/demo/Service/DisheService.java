package com.Menu.demo.Service;

import com.Menu.demo.Entity.Dishe;

import com.Menu.demo.Entity.TypeOfDish;
import com.Menu.demo.Repository.DisheRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DisheService {

    @Autowired
    private DisheRepository disheRepository;

    public List<Dishe> findByCategoryId(Integer categoryId) {
        return disheRepository.findByCategory(categoryId);
    }

    // 1. Найти блюдо по ID
    public Dishe findById(Integer id) {
        return disheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Блюдо с ID " + id + " не найдено"));
    }

    public void saveDishe(Dishe dishe)
    {
        disheRepository.save(dishe);
    }


    public void deleteDishe(Integer id)
    {
        disheRepository.deleteById(id);
    }


}


