package com.Menu.demo.Controller;

import com.Menu.demo.Dto.*;
import com.Menu.demo.Entity.Component;
import com.Menu.demo.Entity.CompositionComponent;
import com.Menu.demo.Entity.CompositionDish;
import com.Menu.demo.Repository.ComponentRepository;
import com.Menu.demo.Repository.DisheRepository;
import com.Menu.demo.Repository.TypeOfDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private DisheRepository dishRepository;

    @Autowired
    private TypeOfDishRepository typeOfDishRepository;

    @Autowired
    private ComponentRepository componentRepository;

    // Главная страница отчетов
    @GetMapping
    public String reportsHome(Model model) {
        // Получаем базовую статистику для главной страницы
        long totalDishes = dishRepository.count();
        long totalComponents = componentRepository.count();
        long totalCategories = typeOfDishRepository.count();

        // Рассчитываем общую стоимость
        List<Object[]> categoryData = dishRepository.findDishesCountByAllCategories();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Object[] row : categoryData) {
            if (row[2] != null) {
                BigDecimal categoryTotal;
                if (row[2] instanceof Double) {
                    categoryTotal = BigDecimal.valueOf((Double) row[2]);
                } else if (row[2] instanceof BigDecimal) {
                    categoryTotal = (BigDecimal) row[2];
                } else {
                    categoryTotal = new BigDecimal(row[2].toString());
                }
                totalRevenue = totalRevenue.add(categoryTotal);
            }
        }

        model.addAttribute("reports", Arrays.asList(
                new ReportInfo("dishes-by-category", "Блюда по категориям"),
                new ReportInfo("components-analysis", "Анализ компонентов"),
                new ReportInfo("nutrition-report", "Отчет по питательности")
        ));
        model.addAttribute("totalDishes", totalDishes);
        model.addAttribute("totalComponents", totalComponents);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalRevenue", totalRevenue);

        return "reports/index";
    }

    // 📊 Отчет 1: Блюда по категориям (простой отчет)
    @GetMapping("/dishes-by-category")
    public String dishesByCategoryReport(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String sortBy,
            Model model) {

        // Получаем категории с количеством блюд
        List<Object[]> rawData;
        if (categoryId != null) {
            // Для конкретной категории
            rawData = dishRepository.findDishesCountByCategoryId(categoryId);
        } else {
            // Для всех категорий
            rawData = dishRepository.findDishesCountByAllCategories();
        }

        // Преобразуем в DTO
        List<CategoryDishesDTO> reportData = new ArrayList<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Object[] row : rawData) {
            CategoryDishesDTO dto = new CategoryDishesDTO();
            dto.setCategoryName((String) row[0]);
            dto.setDishCount(((Number) row[1]).longValue()); // Безопасное приведение

            // Безопасное преобразование Double в BigDecimal
            if (row[2] instanceof Double) {
                dto.setTotalPrice(BigDecimal.valueOf((Double) row[2]));
            } else if (row[2] instanceof BigDecimal) {
                dto.setTotalPrice((BigDecimal) row[2]);
            } else if (row[2] != null) {
                dto.setTotalPrice(new BigDecimal(row[2].toString()));
            }

            if (row[3] instanceof Double) {
                dto.setAvgPrice(BigDecimal.valueOf((Double) row[3]));
            } else if (row[3] instanceof BigDecimal) {
                dto.setAvgPrice((BigDecimal) row[3]);
            } else if (row[3] != null) {
                dto.setAvgPrice(new BigDecimal(row[3].toString()));
            }

            if (dto.getTotalPrice() != null) {
                totalRevenue = totalRevenue.add(dto.getTotalPrice());
            }

            reportData.add(dto);
        }

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

        // Расчет доли для каждой категории
        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            for (CategoryDishesDTO dto : reportData) {
                if (dto.getTotalPrice() != null) {
                    BigDecimal share = dto.getTotalPrice()
                            .divide(totalRevenue, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    dto.setRevenueShare(share);
                }
            }
        }

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("categories", typeOfDishRepository.findAll());
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("sortBy", sortBy);
        long totalDishesCount = reportData.stream()
                .mapToLong(dto -> dto.getDishCount() != null ? dto.getDishCount() : 0L)
                .sum();
        model.addAttribute("totalDishesCount", totalDishesCount);

        return "reports/dishes-by-category";
    }

    // 🥗 Отчет 2: Анализ компонентов (какие компоненты в каких блюдах используются)
    @GetMapping("/components-analysis")
    public String componentsAnalysisReport(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        // Получаем все компоненты
        List<Component> components = componentRepository.findAll();

        List<ComponentAnalysisDTO> reportData = new ArrayList<>();

        for (Component component : components) {
            // Фильтрация по цене
            if (minPrice != null && component.getPrice() != null
                    && component.getPrice().doubleValue() < minPrice) {
                continue;
            }
            if (maxPrice != null && component.getPrice() != null
                    && component.getPrice().doubleValue() > maxPrice) {
                continue;
            }

            ComponentAnalysisDTO dto = new ComponentAnalysisDTO();
            dto.setComponentId(component.getCodeComponent());
            dto.setComponentName(component.getTitle());
            dto.setPrice(component.getPrice());
            dto.setCalorie(component.getCalorie());
            dto.setWeight(component.getWeight());

            // Считаем, в скольких блюдах используется компонент
            long usedInDishes = component.getCompositionDishes().stream()
                    .map(CompositionDish::getDish)
                    .distinct()
                    .count();
            dto.setUsedInDishes(usedInDishes);

            // Получаем список блюд, где используется компонент
            List<String> dishNames = component.getCompositionDishes().stream()
                    .map(cd -> cd.getDish() != null ? cd.getDish().getTitle() : "Неизвестно")
                    .distinct()
                    .collect(Collectors.toList());
            dto.setDishNames(dishNames);

            reportData.add(dto);
        }

        // Сортировка по популярности (сколько блюд используют компонент)
        reportData.sort((a, b) -> Long.compare(b.getUsedInDishes(), a.getUsedInDishes()));

        // Итоги
        long totalComponents = reportData.size();
        long totalUsage = reportData.stream().mapToLong(ComponentAnalysisDTO::getUsedInDishes).sum();

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalComponents", totalComponents);
        model.addAttribute("totalUsage", totalUsage);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "reports/components-analysis";
    }

    // 📈 Отчет 3: Анализ питательности (компоненты + микроэлементы)
    @GetMapping("/nutrition-report")
    public String nutritionReport(Model model) {

        // Получаем компоненты с их микроэлементами
        List<Component> components = componentRepository.findAll();

        List<NutritionReportDTO> reportData = new ArrayList<>();

        for (Component component : components) {
            NutritionReportDTO dto = new NutritionReportDTO();
            dto.setComponentName(component.getTitle());
            dto.setCalorie(component.getCalorie());
            dto.setPrice(component.getPrice());

            // Собираем информацию о микроэлементах
            List<MicroelementInfoDTO> microelements = new ArrayList<>();
            BigDecimal totalMicroelements = BigDecimal.ZERO;

            for (CompositionComponent compComp : component.getCompositionComponents()) {
                if (compComp.getMicroelement() != null && compComp.getQuantityPer100() != null) {
                    MicroelementInfoDTO microDto = new MicroelementInfoDTO();
                    microDto.setMicroelementName(compComp.getMicroelement().getTitle());
                    microDto.setQuantityPer100(compComp.getQuantityPer100());

                    microelements.add(microDto);

                    if (compComp.getQuantityPer100() != null) {
                        totalMicroelements = totalMicroelements.add(compComp.getQuantityPer100());
                    }
                }
            }

            dto.setMicroelements(microelements);
            dto.setTotalMicroelements(totalMicroelements);

            // Вычисляем "ценность" компонента (калории на рубль)
            if (component.getCalorie() != null && component.getPrice() != null
                    && component.getPrice().compareTo(BigDecimal.ZERO) > 0) {

                BigDecimal value = component.getCalorie()
                        .divide(component.getPrice(), 2, RoundingMode.HALF_UP);
                dto.setCaloriePerRuble(value);
            }

            reportData.add(dto);
        }

        // Сортировка по питательной ценности
        reportData.sort((a, b) -> {
            BigDecimal valueA = a.getCaloriePerRuble() != null ? a.getCaloriePerRuble() : BigDecimal.ZERO;
            BigDecimal valueB = b.getCaloriePerRuble() != null ? b.getCaloriePerRuble() : BigDecimal.ZERO;
            return valueB.compareTo(valueA); // По убыванию
        });

        // Итоги
        BigDecimal totalCalories = reportData.stream()
                .map(NutritionReportDTO::getCalorie)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = reportData.stream()
                .map(NutritionReportDTO::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("reportData", reportData);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalPrice", totalPrice);

        return "reports/nutrition-report";
    }
}