package com.example.productshopxml.service.Impl;

import com.example.productshopxml.data.dto.exports.categoriesbyproductscount.CategoryDTO;
import com.example.productshopxml.data.dto.exports.categoriesbyproductscount.CategoryRootDTO;
import com.example.productshopxml.data.dto.imports.CategorySeedRootDTO;
import com.example.productshopxml.data.dto.imports.CategorySeedDTO;
import com.example.productshopxml.data.entities.Category;
import com.example.productshopxml.data.entities.Product;
import com.example.productshopxml.repository.CategoryRepository;
import com.example.productshopxml.service.CategoryService;
import com.example.productshopxml.util.validator.ValidatorService;
import com.example.productshopxml.util.xml.XMLParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final String FILE_INPUT_PATH = "src/main/resources/input/categories.xml";
    private final String FILE_OUTPUT_PATH = "src/main/resources/output/categoriesByProductsCount.xml";

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final ValidatorService validatorService;
    private final XMLParser xmlParser;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper, ValidatorService validatorService, XMLParser xmlParser) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.validatorService = validatorService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCategory() throws JAXBException {
        if (categoryRepository.count() == 0) {
            CategorySeedRootDTO categoryRootDTO = xmlParser.parse(CategorySeedRootDTO.class, FILE_INPUT_PATH);
            for (CategorySeedDTO categoryDTO : categoryRootDTO.getCategoryDTOList()) {
                if (!validatorService.isValid(categoryRootDTO)) {
                    validatorService.getViolation(categoryRootDTO).forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                Category category = mapper.map(categoryDTO, Category.class);
                categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public void getCategoriesAndProductsCount() throws JAXBException {
        List<CategoryDTO> categoryDTOList = categoryRepository.getAllBy()
                .stream().map(category -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    int count = category.getProducts().size();
                    categoryDTO.setProductsCount(count);
                    BigDecimal totalRevenue = category.getProducts()
                            .stream()
                            .map(Product::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    categoryDTO.setTotalRevenue(totalRevenue);
                    categoryDTO.setAvrPrice(totalRevenue
                            .divide(BigDecimal.valueOf(count), 6, RoundingMode.HALF_UP)
                            .stripTrailingZeros());
                    return categoryDTO;
                }).sorted(Comparator.comparing(CategoryDTO::getProductsCount))
                .toList();

        CategoryRootDTO categoryRootDTO = new CategoryRootDTO();
        categoryRootDTO.setCategoryDTOList(categoryDTOList);
        xmlParser.exportToFile(categoryRootDTO, FILE_OUTPUT_PATH);
    }
}