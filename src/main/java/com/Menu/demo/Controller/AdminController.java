//package com.Menu.demo.Controller;
//
//import com.Menu.demo.Entity.Dishe;
//import com.Menu.demo.Service.ComponentService;
//import com.Menu.demo.Service.DisheService;
//import com.Menu.demo.Service.TypeOfDishService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
////@CrossOrigin(origins = "http://localhost:3000")
//public class AdminController {
//
//    @Autowired
//    private DisheService disheService;
//
//    @Autowired
//    private ComponentService componentService;
//
//    @Autowired
//    private TypeOfDishService typeOfDishService;
//
//    // Dishes CRUD
//    @GetMapping("/dishes")
//    public List<Dishe> getAllDishes() {
//        return disheService.findAll();
//    }
//
//    @PostMapping("/dishes")
//    public Dishe createDish(@RequestBody Dishe dish) {
//        return disheService.save(dish);
//    }
//
//    @PutMapping("/dishes/{id}")
//    public Dishe updateDish(@PathVariable Integer id, @RequestBody Dishe dish) {
//        dish.setDishesId(id);
//        return disheService.save(dish);
//    }
//
//    @DeleteMapping("/dishes/{id}")
//    public void deleteDish(@PathVariable Integer id) {
//        disheService.delete(id);
//    }
//
//    // Аналогичные endpoints для Components, Microelements, etc.
//}