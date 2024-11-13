package com.example.json;

import com.example.json.service.CategoryService;
import com.example.json.service.ProductService;
import com.example.json.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

    public Main(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
//        categoryService.seedCategory();
//        userService.seedUser();
//        productService.seedProduct();
//        productService.printAllProducts();
//        userService.printUserAndSoldProduct();
//        categoryService.printCategories();
//        userService.printUserAndProduct();
    }
}
