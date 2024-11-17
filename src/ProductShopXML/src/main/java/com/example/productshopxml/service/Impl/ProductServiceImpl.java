package com.example.productshopxml.service.Impl;

import com.example.productshopxml.data.dto.exports.productsinrange.ProductDTO;
import com.example.productshopxml.data.dto.exports.productsinrange.ProductRootDTO;
import com.example.productshopxml.data.dto.imports.ProductSeedDTO;
import com.example.productshopxml.data.dto.imports.ProductSeedRootDTO;
import com.example.productshopxml.data.entities.Category;
import com.example.productshopxml.data.entities.Product;
import com.example.productshopxml.data.entities.User;
import com.example.productshopxml.repository.CategoryRepository;
import com.example.productshopxml.repository.ProductRepository;
import com.example.productshopxml.repository.UserRepository;
import com.example.productshopxml.service.ProductService;
import com.example.productshopxml.util.validator.ValidatorService;
import com.example.productshopxml.util.xml.XMLParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductServiceImpl implements ProductService {

    private final String FILE_INPUT_PATH = "src/main/resources/input/products.xml";
    private final String FILE_OUTPUT_PATH = "src/main/resources/output/productsInRange.xml";

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final ValidatorService validatorService;
    private final XMLParser xmlParser;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper mapper, ValidatorService validatorService, XMLParser xmlParser) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.validatorService = validatorService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedProducts() throws JAXBException {
        if (productRepository.count() == 0) {
            ProductSeedRootDTO productRootDTO = xmlParser.parse(ProductSeedRootDTO.class, FILE_INPUT_PATH);
            for (ProductSeedDTO productDTO : productRootDTO.getProductDTOS()) {
                if (!validatorService.isValid(productDTO)) {
                    validatorService.getViolation(productDTO).forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }

                Product product = mapper.map(productDTO, Product.class);
                product.setBuyer(getRandomUser(true));
                product.setSeller(getRandomUser(false));
                product.setCategories(getRandomCategory());
                productRepository.saveAndFlush(product);
            }
        }
    }

    @Override
    public void getProductsInRange() throws JAXBException {
        List<ProductDTO> productDTOList = productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream().map(p -> mapper.map(p, ProductDTO.class))
                .toList();
        ProductRootDTO productRootDTO = new ProductRootDTO();
        productRootDTO.setProductDTOList(productDTOList);
        xmlParser.exportToFile(productRootDTO, FILE_OUTPUT_PATH);
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
