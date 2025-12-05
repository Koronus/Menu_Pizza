//package com.Menu.demo.Controller;
//
//import com.Menu.demo.Entity.Dishe;
//import com.Menu.demo.Entity.TypeOfDish;
//import com.Menu.demo.Service.DisheService;
//import com.Menu.demo.Service.TypeOfDishService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Controller
//public class MainConrtoller {
//
////    @Autowired
////    private TypeOfDishService typeOfDishService;
////
////    @Autowired
////    private DisheService disheService;
////
////    @GetMapping("/")
////    public List<TypeOfDish> getAllTypes(){
////        return typeOfDishService.findAll();
////    }
////
////    @GetMapping("/list")
////    public List<Dishe> getAllDishes(){
////        return disheService.findAll();
////    }
//    @GetMapping("/")
//    public String index() {
//        return "index.html"; // вернет static/index.html
//    }
//
//    @GetMapping("/menu")
//    public String menu() {
//        return "index.html"; // вернет static/index.html
//    }
//
//}
