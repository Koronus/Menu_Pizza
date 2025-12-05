package com.Menu.demo.Repository;

import com.Menu.demo.Entity.Dishe;
import com.Menu.demo.Entity.TypeOfDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DisheRepository extends JpaRepository<Dishe, Integer> {
    @Query("SELECT d FROM Dishe d WHERE d.typeOfDish.codeType = :categoryId")
    List<Dishe> findByCategory(@Param("categoryId") Integer categoryId);



}