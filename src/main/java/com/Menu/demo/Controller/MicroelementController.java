package com.Menu.demo.Controller;

import com.Menu.demo.Entity.Component;
import com.Menu.demo.Entity.CompositionComponent;
import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Entity.Dishe;
import com.Menu.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categorys/dishes/components/microelements/{componentId}")
public class MicroelementController {

    @Autowired
    private CompositionComponentService compositionComponentService;



    @Autowired
    private ComponentService componentService;

    @Autowired
    private MicroelementService microelementService;

    // 1. Показать все компоненты блюда
    @GetMapping
    public String showMicroelments(@PathVariable("componentId") Integer componentId, Model model) {
        // Получаем компонент
        Component component = componentService.findById(componentId);

        // Получаем микроэелементы этого компонента
        List<CompositionComponent> compositions = compositionComponentService.findByComponentCode(componentId);

        // Получаем все доступные компоненты
        var allMicroelements = microelementService.findAll();

        model.addAttribute("component", component);
        model.addAttribute("compositions", compositions);
        model.addAttribute("allMicroelements", allMicroelements);


        return "microelementList";
    }



}
