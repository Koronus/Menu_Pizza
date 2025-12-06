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

    @GetMapping("/new/{categoryId}")
    public String createDishForm(@PathVariable("categoryId") Integer categoryId, Model model) {
        // Передаем ID категории в модель


        // Получаем категорию из БД
        TypeOfDish category = typeOfDishService.getTypeOfDishById(categoryId);

        // Создаем новое пустое блюдо
        Dishe dish = new Dishe();

        dish.setTypeOfDish(category);

        model.addAttribute("dish", dish);
        return "forms/dishes-form";
    }

    @PostMapping("/save")
    public String saveDishe(@ModelAttribute("Dishe") Dishe dishe)
    {
        // Сохраняем блюдо
        disheService.saveDishe(dishe);

        // Получаем ID категории из сохраненного блюда
        Integer categoryId = dishe.getTypeOfDish().getCodeType();

        // Перенаправляем на страницу категории
        return "redirect:/categorys/dishes/" + categoryId;
    }

    //Измениить блюдо
    @GetMapping("/edit/{id}")
    public String editTypeOfDish(@PathVariable("id") Integer id, Model model)
    {
        model.addAttribute("dish",disheService.findById(id));
        return "forms/dishes-form";
    }

    // Удалить блюдо
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable("id") Integer dishId) {
        // Получаем блюдо, чтобы узнать его категорию
        Dishe dish = disheService.findById(dishId);
        Integer categoryId = dish.getTypeOfDish().getCodeType();

        disheService.deleteDishe(dishId);

        // Перенаправляем обратно в категорию
        return "redirect:/categorys/dishes/" + categoryId;
    }
}

