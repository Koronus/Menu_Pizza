package com.Menu.demo.Controller;


import com.Menu.demo.Entity.Dishe;
import com.Menu.demo.Entity.TypeOfDish;
import com.Menu.demo.Repository.DisheRepository;
import com.Menu.demo.Service.DisheService;
import com.Menu.demo.Service.TypeOfDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categorys/dishes")
public class DisheController {

    @Autowired
    private DisheService disheService;

    @Autowired
    private TypeOfDishService typeOfDishService;

    // Показать блюда по категории
    @GetMapping("/{categoryId}")
    public String showDishesByCategory(@PathVariable("categoryId") Integer categoryId,
                                       Model model) {
        List<Dishe> dishes = disheService.findByCategoryId(categoryId);
        model.addAttribute("dishes", dishes);
        model.addAttribute("categoryId", categoryId);
        return "dishesList";
    }

    @GetMapping("/new")
    public String newDishe(Model model){

        Dishe dish =new Dishe();

        //TypeOfDish category = typeOfDishService.getTypeOfDishById(categoryId);
        //dish.setTypeOfDish(category); // ← устанавливаем полный объект

        //dish.setTypeOfDish(category);

        model.addAttribute("dish",dish);
       // model.addAttribute("category",categoryId);


        return "forms/dishes-form";
    }

    @PostMapping("/save")
    public String saveDishe(@ModelAttribute("Dishe") Dishe dishe)
    {
        disheService.saveDishe(dishe);
        return "redirect:/dishes";
    }

    // Удалить блюдо (простой путь)
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable("id") Integer dishId) {
        disheService.deleteDishe(dishId);
        return "redirect:/dishes";
    }
}

