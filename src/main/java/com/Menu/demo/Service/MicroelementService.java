package com.Menu.demo.Service;

import com.Menu.demo.Entity.Component;
import com.Menu.demo.Entity.Microelement;
import com.Menu.demo.Repository.ComponentRepository;
import com.Menu.demo.Repository.MicroelementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MicroelementService {

    @Autowired
    private MicroelementRepository microelementRepository;

    public List<Microelement> findAll() {
        return microelementRepository.findAll();
    }

    public void saveMicroelement(Microelement microelement) { microelementRepository.save(microelement);}

    public Microelement findById(Integer id) {
        return microelementRepository.findById(id).orElseThrow(() -> new RuntimeException("Микроэлемент с ID " + id + " не найдено"));
    }

    public void deleteMicroelement(Integer id) {microelementRepository.deleteById(id);}
}
