package com.Menu.demo.Service;

import com.Menu.demo.Dto.CategoryDishesReportDTO;
import com.Menu.demo.Dto.ComponentAnalysisReportDTO;
import com.Menu.demo.Dto.MicroelementInfoDTO;
import com.Menu.demo.Dto.NutritionReportDTO;
import com.Menu.demo.Repository.ComponentRepository;
import com.Menu.demo.Repository.DisheRepository;
import com.Menu.demo.Repository.TypeOfDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {

    @Autowired
    private DisheRepository disheRepository;

    @Autowired
    private TypeOfDishRepository typeOfDishRepository;

    @Autowired
    private ComponentRepository componentRepository;

    // 1. Отчет: Блюда по категориям
    public List<CategoryDishesReportDTO> getCategoryDishesReport(Integer categoryId) {
        List<Object[]> rawData;

        if (categoryId != null) {
            rawData = disheRepository.findDishesCountByCategoryId(categoryId);
        } else {
            rawData = disheRepository.findDishesCountByAllCategories();
        }

        List<CategoryDishesReportDTO> result = new ArrayList<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Object[] row : rawData) {
            CategoryDishesReportDTO dto = new CategoryDishesReportDTO();
            dto.setCategoryName((String) row[0]);
            dto.setDishCount(((Number) row[1]).longValue());

            if (row[2] instanceof Number) {
                dto.setTotalPrice(new BigDecimal(row[2].toString()));
            }

            if (row[3] instanceof Number) {
                dto.setAvgPrice(new BigDecimal(row[3].toString()));
            }

            if (dto.getTotalPrice() != null) {
                totalRevenue = totalRevenue.add(dto.getTotalPrice());
            }

            result.add(dto);
        }

        // Расчет доли для каждой категории
        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            for (CategoryDishesReportDTO dto : result) {
                if (dto.getTotalPrice() != null) {
                    BigDecimal share = dto.getTotalPrice()
                            .divide(totalRevenue, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    dto.setRevenueShare(share);
                }
            }
        }

        return result;
    }

    // 2. Отчет: Анализ компонентов
    public List<ComponentAnalysisReportDTO> getComponentsAnalysisReport(Double minPrice, Double maxPrice) {
        List<Object[]> rawData = componentRepository.findComponentsAnalysis(minPrice, maxPrice);
        List<ComponentAnalysisReportDTO> result = new ArrayList<>();

        for (Object[] row : rawData) {
            ComponentAnalysisReportDTO dto = new ComponentAnalysisReportDTO();
            dto.setComponentId(((Number) row[0]).intValue());
            dto.setComponentName((String) row[1]);

            if (row[2] instanceof Number) {
                dto.setPrice(new BigDecimal(row[2].toString()));
            }

            if (row[3] instanceof Number) {
                dto.setCalorie(new BigDecimal(row[3].toString()));
            }

            if (row[4] instanceof Number) {
                dto.setWeight(new BigDecimal(row[4].toString()));
            }

            dto.setUsedInDishes(((Number) row[5]).longValue());

            // Безопасное преобразование массива PostgreSQL в List<String>
            if (row[6] != null) {
                String[] dishArray = (String[]) row[6];
                // Фильтруем null значения перед созданием списка
                List<String> dishList = new ArrayList<>();
                for (String dish : dishArray) {
                    if (dish != null) {
                        dishList.add(dish);
                    }
                }
                dto.setDishNames(dishList);
            } else {
                dto.setDishNames(new ArrayList<>());
            }

            result.add(dto);
        }

        return result;
    }

    // 3. Отчет: Анализ питательности
    public List<NutritionReportDTO> getNutritionReport() {
        List<Object[]> rawData = componentRepository.findNutritionReportData();
        Map<String, NutritionReportDTO> resultMap = new HashMap<>();

        for (Object[] row : rawData) {
            String componentName = (String) row[0];

            NutritionReportDTO dto = resultMap.computeIfAbsent(componentName, k -> {
                NutritionReportDTO newDto = new NutritionReportDTO();
                newDto.setComponentName(componentName);

                if (row[1] instanceof Number) {
                    newDto.setCalorie(new BigDecimal(row[1].toString()));
                }

                if (row[2] instanceof Number) {
                    newDto.setPrice(new BigDecimal(row[2].toString()));

                    // Расчет калорий на рубль
                    if (newDto.getCalorie() != null && newDto.getPrice() != null
                            && newDto.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal value = newDto.getCalorie()
                                .divide(newDto.getPrice(), 2, RoundingMode.HALF_UP);
                        newDto.setCaloriePerRuble(value);
                    }
                }

                newDto.setMicroelements(new ArrayList<>());
                newDto.setTotalMicroelements(BigDecimal.ZERO);

                return newDto;
            });

            // Добавляем микроэлемент, если он есть
            if (row[3] != null && row[4] != null) {
                MicroelementInfoDTO microDto = new MicroelementInfoDTO();
                microDto.setMicroelementName((String) row[3]);
                microDto.setQuantityPer100(new BigDecimal(row[4].toString()));

                dto.getMicroelements().add(microDto);
                dto.setTotalMicroelements(
                        dto.getTotalMicroelements().add(microDto.getQuantityPer100())
                );
            }
        }

        // Сортировка по питательной ценности (калории на рубль)
        return resultMap.values().stream()
                .sorted((a, b) -> {
                    BigDecimal valueA = a.getCaloriePerRuble() != null ? a.getCaloriePerRuble() : BigDecimal.ZERO;
                    BigDecimal valueB = b.getCaloriePerRuble() != null ? b.getCaloriePerRuble() : BigDecimal.ZERO;
                    return valueB.compareTo(valueA);
                })
                .collect(Collectors.toList());
    }

    // Общая статистика для главной страницы отчетов
    public Map<String, Object> getReportStatistics() {
        Map<String, Object> stats = new HashMap<>();

        long totalDishes = disheRepository.count();
        long totalComponents = componentRepository.count();
        long totalCategories = typeOfDishRepository.count();

        List<Object[]> categoryData = disheRepository.findDishesCountByAllCategories();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Object[] row : categoryData) {
            if (row[2] instanceof Number) {
                totalRevenue = totalRevenue.add(new BigDecimal(row[2].toString()));
            }
        }

        stats.put("totalDishes", totalDishes);
        stats.put("totalComponents", totalComponents);
        stats.put("totalCategories", totalCategories);
        stats.put("totalRevenue", totalRevenue);

        return stats;
    }
}