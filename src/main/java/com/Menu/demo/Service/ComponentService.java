package com.Menu.demo.Service;

import com.Menu.demo.Entity.*;
import com.Menu.demo.Repository.ComponentRepository;
//import com.Menu.demo.Repository.CompositionComponentRepository;
//import com.Menu.demo.Repository.MicroelementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    public List<Component> findAll() {
        return componentRepository.findAll();
    }

    public Component findById(Integer id) {
        return componentRepository.findById(id).orElseThrow(() -> new RuntimeException("Блюдо с ID " + id + " не найдено"));
    }

    public void saveComponent(Component component) { componentRepository.save(component);}

}
