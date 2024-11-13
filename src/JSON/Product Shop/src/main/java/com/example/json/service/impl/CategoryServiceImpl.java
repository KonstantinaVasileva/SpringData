package com.example.json.service.impl;

import com.example.json.data.dtos.exports.CategoryByProductsCountDto;
import com.example.json.data.dtos.imports.CategorySeedDTO;
import com.example.json.data.entities.Category;
import com.example.json.data.entities.Product;
import com.example.json.repository.CategoryRepository;
import com.example.json.service.CategoryService;
import com.example.json.util.ValidatorService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final ValidatorService validator;
    private final Gson gson;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(ModelMapper mapper, ValidatorService validator, Gson gson, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategory() throws FileNotFoundException {
        if (categoryRepository.count() == 0) {
            CategorySeedDTO[] categorySeedDTO =
                    gson.fromJson(new FileReader("src/main/resources/json/categories.json"),
                            CategorySeedDTO[].class);
            for (CategorySeedDTO seedDTO : categorySeedDTO) {
                if (!validator.isValid(seedDTO)) {
                    validator.getViolation(seedDTO)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                Category category = mapper.map(seedDTO, Category.class);
                categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public Set<CategoryByProductsCountDto> categoriesByProductsCount() {
        Set<Category> categoriesBy = categoryRepository.getCategoriesBy();
        Set<CategoryByProductsCountDto> categoryByProductsCountDtos = new HashSet<>();
        for (Category c : categoriesBy) {
            int size = c.getProducts().size();
            BigDecimal sum = BigDecimal.ZERO;
            for (Product p : c.getProducts()) {
                sum = sum.add(p.getPrice());
            }

            CategoryByProductsCountDto map = mapper.map(c, CategoryByProductsCountDto.class);
            map.setProductCount(size);
            map.setTotalRevenue(sum);
            map.setAvrPrice(sum.divide(new BigDecimal(size), 6, RoundingMode.HALF_UP));
            categoryByProductsCountDtos.add(map);
        }

        return categoryByProductsCountDtos;
    }

    @Override
    public void printCategories(){
        List<CategoryByProductsCountDto> categoryByProductsCountDtos = categoriesByProductsCount().stream()
                .sorted(Comparator.comparing(CategoryByProductsCountDto::getProductCount))
                .toList();
        System.out.println(gson.toJson(categoryByProductsCountDtos));
    }
}
