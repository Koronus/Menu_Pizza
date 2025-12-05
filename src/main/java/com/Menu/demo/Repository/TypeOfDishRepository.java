package com.Menu.demo.Repository;

import com.Menu.demo.Entity.TypeOfDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfDishRepository extends JpaRepository<TypeOfDish, Integer>{
    @Query("SELECT t FROM TypeOfDish t")
    List<TypeOfDish> findByType();

}
