package com.Menu.demo.Controller;

import com.Menu.demo.Entity.*;
import com.Menu.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/categorys/dishes/components/microelements")
public class MicroelementController {

    @Autowired
    private CompositionComponentService compositionComponentService;



    @Autowired
    private ComponentService componentService;

    @Autowired
    private MicroelementService microelementService;

    // 1. Показать все компоненты блюда
    @GetMapping("/{codeComponent}")
    public String showMicroelements(@PathVariable("codeComponent") Integer codeComponent, Model model) {
        // Получаем компонент
        Component component = componentService.findById(codeComponent);

        // Получаем микроэелементы этого компонента
        List<CompositionComponent> compositions = compositionComponentService.findByComponentCode(codeComponent);

        // Получаем все доступные компоненты
        var allMicroelements = microelementService.findAll();

        model.addAttribute("component", component);
        model.addAttribute("compositions", compositions);
        model.addAttribute("allMicroelements", allMicroelements);


        return "microelementList";
    }

    @GetMapping("/new/{componentId}")
    public String createMicroelementForm(@PathVariable("componentId") Integer componentId, Model model) {
        // Передаем ID микроэлемента в модель
        Microelement microelement = new Microelement();



        // Получаем компонент из БД
        Component component = componentService.findById(componentId);

        // Создаем новый объект Component


        // Создаем новый объект CompositionDish (связующая таблица)
        //CompositionDish compositionDish = new CompositionDish();

        // Устанавливаем dishId (часть составного ключа)


        //compositionDish.setDish(dish);
        model.addAttribute("component", component);
        model.addAttribute("componentId", componentId);
        model.addAttribute("microelement", microelement);
        model.addAttribute("quantity", 1);
        return "forms/microelements-form";
    }

    @PostMapping("/save")
    public String saveMicroelement(@ModelAttribute("microelement") Microelement microelement,
                                @RequestParam("componentId") Integer componentId,
                                @RequestParam(value = "quantity", defaultValue = "1") BigDecimal quantity)
    {



        CompositionComponent compositionComponent = new CompositionComponent();

        // Сохраняем компонент блюда
        microelementService.saveMicroelement(microelement);

        //первичный ключ для compositionDish
        compositionComponent.setComponentId(componentId);
        compositionComponent.setMicroelementId(microelement.getCodeMicroelement());


        //Внешний ключ для compositionDish
        compositionComponent.setComponent(componentService.findById(componentId));
        compositionComponent.setMicroelement(microelement);


        //Сохраняем количество
        compositionComponent.setQuantityPer100(quantity);

        // Сохраняем в бд

        compositionComponentService.saveCompositionComponent(compositionComponent);

        // Устанавливаем CodeComponent (часть составного ключа)
        // compositionComponent.setComponentId(component.getCodeComponent());

        // Сохраняем сотавной ключ
        //compositionDishService.saveCompositionDishe(compositionDish);


        // Получаем ID блюда
        //Integer codeComponentId = compositionComponent.getComponentId();

        // Перенаправляем на страницу компонента
        return "redirect:/categorys/dishes/components/microelements/" + componentId ;
    }

    // Редактирование существующего компонента в составе блюда
    @GetMapping("/edit/{componentId}/{codeMicroelement}")
    public String editMicroelementInComponent(@PathVariable("componentId") Integer componentId,
                                      @PathVariable("codeMicroelement") Integer codeMicroelement,
                                      Model model) {

        // Находим связь между блюдом и компонентом
        CompositionComponent compositionComponent = compositionComponentService.findByComponentIdAndMicroelementId(componentId,codeMicroelement);

        if (compositionComponent == null) {
            // Если связь не найдена, редирект на список микроэлементов
            return "redirect:/categorys/dishes/components/microelements/" + componentId;
        }

        Component component = componentService.findById(componentId);
        Microelement microelement = microelementService.findById(codeMicroelement);

        model.addAttribute("component", component);
        model.addAttribute("componentId", componentId);
        model.addAttribute("microelement", microelement);
        model.addAttribute("quantity", compositionComponent.getQuantityPer100());
        // model.addAttribute("isEdit", true);

        return "forms/microelements-form";
    }
    // Удалить компонент
    @GetMapping("/delete/{componentId}/{codeMicroelement}")
    public String deleteDish(@PathVariable("componentId") Integer componentId,
                             @PathVariable("codeMicroelement") Integer codeMicroelement,
                             Model model) {
        // Находим связь между блюдом и компонентом
        CompositionComponent compositionComponent = compositionComponentService.findByComponentIdAndMicroelementId(componentId, codeMicroelement);

        //Integer categoryId = dish.getTypeOfDish().getCodeType();
        //Уаление связи между блюдом и его компонентом
        compositionComponentService.deleteRelationMicroelement(componentId,codeMicroelement);
        //Уаление самого микроэлемента
        microelementService.deleteMicroelement(codeMicroelement);

        // Перенаправляем обратно в категорию
        return "redirect:/categorys/dishes/components/microelements/" + componentId;
    }

}
