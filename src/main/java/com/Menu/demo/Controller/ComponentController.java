package com.Menu.demo.Controller;

import com.Menu.demo.Entity.Component;
import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Entity.Dishe;
import com.Menu.demo.Entity.TypeOfDish;
import com.Menu.demo.Service.ComponentService;
import com.Menu.demo.Service.CompositionDisheService;
import com.Menu.demo.Service.DisheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categorys/dishes/components")
public class ComponentController {

    @Autowired
    private CompositionDisheService compositionDishService;

    @Autowired
    private DisheService dishService;

    @Autowired
    private ComponentService componentService;



    //Добавление новго компонента
    @GetMapping("/new/{dishId}")
    public String createDishForm(@PathVariable("dishId") Integer dishId, Model model) {
        // Передаем ID категории в модель
        Component component = new Component();



        // Получаем блюдо из БД
        Dishe dish = dishService.findById(dishId);

        // Создаем новый объект Component


        // Создаем новый объект CompositionDish (связующая таблица)
        //CompositionDish compositionDish = new CompositionDish();

        // Устанавливаем dishId (часть составного ключа)


        //compositionDish.setDish(dish);
        model.addAttribute("dish", dish);
        model.addAttribute("dishId", dishId);
        model.addAttribute("component", component);
        model.addAttribute("quantity", 1);
        return "forms/components-form";
    }

    @PostMapping("/save")
    public String saveComponent(@ModelAttribute("Component") Component component,
                                @RequestParam("dishId") Integer dishId,
                                @RequestParam(value = "quantity", defaultValue = "1") Integer quantity)
    {



        CompositionDish compositionDish = new CompositionDish();

        // Сохраняем компонент блюда
       componentService.saveComponent(component);

       //первичный ключ для compositionDish
        compositionDish.setDishId(dishId);
        compositionDish.setComponentId(component.getCodeComponent());


       //Внешний ключ для compositionDish
        compositionDish.setDish(dishService.findById(dishId));
        compositionDish.setComponent(component);


        //Сохраняем количество
        compositionDish.setQuantity(quantity);

        // Сохраняем в бд

        compositionDishService.saveCompositionDishe(compositionDish);

        // Устанавливаем CodeComponent (часть составного ключа)
         compositionDish.setComponentId(component.getCodeComponent());

        // Сохраняем сотавной ключ
         compositionDishService.saveCompositionDishe(compositionDish);


        // Получаем ID блюда
        Integer dishesId = compositionDish.getDishId();

        // Перенаправляем на страницу блюда
        return "redirect:/categorys/dishes/components/" + dishesId ;
    }

//    //Измениить блюдо
//    @GetMapping("/edit/{dishId}")
//    public String editTypeOfDish(@PathVariable("id") Integer id, Model model)
//    {
//        model.addAttribute("dish", dishService.findById(id));
//        model.addAttribute("dishId", dishService.findById(id).getDishesId());
//        model.addAttribute("component", componentService.findById());
//        model.addAttribute("quantity", 1);
//        return "forms/dishes-form";
//    }


    // 1. Показать все компоненты блюда
    @GetMapping("/{dishId}")
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
