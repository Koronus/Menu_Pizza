package com.Menu.demo.Controller;

import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Entity.Dishe;
import com.Menu.demo.Service.ComponentService;
import com.Menu.demo.Service.CompositionDisheService;
import com.Menu.demo.Service.DisheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categorys/dishes/components/{dishId}")
public class ComponentController {

    @Autowired
    private CompositionDisheService compositionDishService;

    @Autowired
    private DisheService dishService;

    @Autowired
    private ComponentService componentService;

    // 1. Показать все компоненты блюда
    @GetMapping
    public String showComponents(@PathVariable("dishId") Integer dishId, Model model) {
        // Получаем блюдо
        Dishe dish = dishService.findById(dishId);

        // Получаем компоненты этого блюда
        List<CompositionDish> compositions = compositionDishService.findByDishId(dishId);

        // Получаем все доступные компоненты
        var allComponents = componentService.findAll();

        model.addAttribute("dish", dish);
        model.addAttribute("compositions", compositions);
        model.addAttribute("allComponents", allComponents);


        return "components";
    }

}
