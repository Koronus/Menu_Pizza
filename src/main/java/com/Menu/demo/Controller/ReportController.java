package com.Menu.demo.Controller;

import com.Menu.demo.Service.ReportService;
import com.Menu.demo.Service.TypeOfDishService;  // Добавьте этот импорт
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private TypeOfDishService typeOfDishService;  // Добавьте эту зависимость

    // Главная страница отчетов
    @GetMapping
    public String reportsHome(Model model) {
        Map<String, Object> stats = reportService.getReportStatistics();

        model.addAttribute("totalDishes", stats.get("totalDishes"));
        model.addAttribute("totalComponents", stats.get("totalComponents"));
        model.addAttribute("totalCategories", stats.get("totalCategories"));
        model.addAttribute("totalRevenue", stats.get("totalRevenue"));

        return "reports/index";
    }

    // Отчет 1: Блюда по категориям
    @GetMapping("/dishes-by-category")
    public String dishesByCategoryReport(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String sortBy,
            Model model) {

        var reportData = reportService.getCategoryDishesReport(categoryId);

        // Сортировка
        if ("price".equals(sortBy)) {
            reportData.sort((a, b) -> {
                BigDecimal priceA = a.getTotalPrice() != null ? a.getTotalPrice() : BigDecimal.ZERO;
                BigDecimal priceB = b.getTotalPrice() != null ? b.getTotalPrice() : BigDecimal.ZERO;
                return priceB.compareTo(priceA);
            });
        } else if ("dishes".equals(sortBy)) {
            reportData.sort((a, b) -> b.getDishCount().compareTo(a.getDishCount()));
        } else if ("name".equals(sortBy)) {
            reportData.sort((a, b) -> a.getCategoryName().compareTo(b.getCategoryName()));
        }

        long totalDishesCount = reportData.stream()
                .mapToLong(dto -> dto.getDishCount() != null ? dto.getDishCount() : 0L)
                .sum();

        BigDecimal totalRevenue = reportData.stream()
                .map(dto -> dto.getTotalPrice() != null ? dto.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Получаем все категории для выпадающего списка
        var categories = typeOfDishService.findAll();  // Добавьте эту строку

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalDishesCount", totalDishesCount);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("categories", categories);  // Добавьте эту строку

        return "reports/dishes-by-category";
    }

    // Отчет 2: Анализ компонентов
    @GetMapping("/components-analysis")
    public String componentsAnalysisReport(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        var reportData = reportService.getComponentsAnalysisReport(minPrice, maxPrice);

        long totalComponents = reportData.size();
        long totalUsage = reportData.stream()
                .mapToLong(dto -> dto.getUsedInDishes() != null ? dto.getUsedInDishes() : 0L)
                .sum();

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalComponents", totalComponents);
        model.addAttribute("totalUsage", totalUsage);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "reports/components-analysis";
    }

    // Отчет 3: Анализ питательности
    @GetMapping("/nutrition-report")
    public String nutritionReport(Model model) {

        var reportData = reportService.getNutritionReport();

        BigDecimal totalCalories = reportData.stream()
                .map(dto -> dto.getCalorie() != null ? dto.getCalorie() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = reportData.stream()
                .map(dto -> dto.getPrice() != null ? dto.getPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalPrice", totalPrice);

        return "reports/nutrition-report";
    }
}