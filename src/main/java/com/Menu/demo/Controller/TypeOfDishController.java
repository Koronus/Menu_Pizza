package com.Menu.demo.Controller;

import com.Menu.demo.Entity.TypeOfDish;
import com.Menu.demo.Service.TypeOfDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categorys")
public class TypeOfDishController {
    @Autowired
    private TypeOfDishService typeOfDishService;

//    @GetMapping("/")
//    public List<TypeOfDish> getAllCategories() {
//        return typeOfDishService.findAll();
//    }

    @GetMapping
    public String index(Model model)
    {
        model.addAttribute("types_of_dishes",typeOfDishService.findAll());
        return "typesList";
    }

    @GetMapping("/new")
    public String newCategory(Model model)
    {
        model.addAttribute("types_of_dishes",new TypeOfDish());
        return "forms/categorys-form";
    }

    @PostMapping("/save")
    public String saveType(@ModelAttribute("TypeOfDish") TypeOfDish typeOfDish)
    {
       typeOfDishService.saveTypeOfDish(typeOfDish);
       return "redirect:/categorys";
    }

    @GetMapping("/delete/{id}")
    public String deleteTypeOfDish(@PathVariable("id") Integer id)
    {
        typeOfDishService.deleteTypeOfDish(id);
        return "redirect:/categorys";
    }

    @GetMapping("/edit/{id}")
    public String editTypeOfDish(@PathVariable("id") Integer id, Model model)
    {
        model.addAttribute("types_of_dishes",typeOfDishService.getTypeOfDishById(id));
        return "forms/categorys-form";
    }

//    @GetMapping("/view/{id}")
//    public String viewTypeOfDish(@PathVariable("id") Integer id, Model model)
//    {
//        model.addAttribute("TypeOfDish",typeOfDishService.getTypeOfDishById(id));
//        return "dishesList";
//    }
}
