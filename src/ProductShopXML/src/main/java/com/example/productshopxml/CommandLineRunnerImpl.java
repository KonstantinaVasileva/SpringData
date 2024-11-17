package com.example.productshopxml;

import com.example.productshopxml.service.CategoryService;
import com.example.productshopxml.service.ProductService;
import com.example.productshopxml.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public CommandLineRunnerImpl(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
        categoryService.seedCategory();
        productService.seedProducts();

//        productService.getProductsInRange();
//        userService.getUsersWithSuccessfullySoldProduct();
        categoryService.getCategoriesAndProductsCount();
    }
}
