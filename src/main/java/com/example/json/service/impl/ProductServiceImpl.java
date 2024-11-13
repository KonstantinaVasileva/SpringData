package com.example.json.service.impl;

import com.example.json.data.dtos.exports.ProductInRangeDto;
import com.example.json.data.dtos.imports.ProductSeedDTO;
import com.example.json.data.entities.Category;
import com.example.json.data.entities.Product;
import com.example.json.data.entities.User;
import com.example.json.repository.CategoryRepository;
import com.example.json.repository.ProductRepository;
import com.example.json.repository.UserRepository;
import com.example.json.service.ProductService;
import com.example.json.util.ValidatorService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;
    private final Gson gson;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorService validatorService;


    public ProductServiceImpl(ModelMapper mapper, Gson gson, ProductRepository productRepository, ValidatorService validatorService, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.gson = gson;
        this.productRepository = productRepository;
        this.validatorService = validatorService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedProduct() throws FileNotFoundException {
        if (productRepository.count() == 0) {
            ProductSeedDTO[] productSeedDTOs =
                    gson.fromJson(new FileReader("src/main/resources/json/products.json"), ProductSeedDTO[].class);

            for (ProductSeedDTO productSeedDTO : productSeedDTOs) {
                if (!validatorService.isValid(productSeedDTO)) {
                    validatorService.getViolation(productSeedDTO)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                Product product = mapper.map(productSeedDTO, Product.class);
                product.setBuyer(getRandomUser(true));
                product.setSeller(getRandomUser(false));
                product.setCategories(getRandomCategory());
                productRepository.saveAndFlush(product);
            }
        }
    }

    @Override
    public Set<ProductInRangeDto> findAllByPriceRangeAndNoBuyer() {
        Set<ProductInRangeDto> output = new HashSet<>();
        Set<Product> products = productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        for (Product product : products) {
            ProductInRangeDto map = mapper.map(product, ProductInRangeDto.class);
            map.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
            output.add(map);
        }
       return output.stream()
               .sorted(Comparator.comparing(ProductInRangeDto::getPrice))
               .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void printAllProducts(){
        System.out.println(gson.toJson(findAllByPriceRangeAndNoBuyer()));
    }

    private Set<Category> getRandomCategory() {
        int count = ThreadLocalRandom.current().nextInt(1, 3);
        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            long id = ThreadLocalRandom.current().nextLong(1, categoryRepository.count() + 1);
            Category category = categoryRepository.findById(id).get();
            categories.add(category);
        }

        return categories;
    }

    private User getRandomUser(boolean isBuyer) {
        long id = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);
        if (isBuyer && id % 5 == 0) {
            return null;
        }
        return userRepository.findById(id).get();
    }
}
