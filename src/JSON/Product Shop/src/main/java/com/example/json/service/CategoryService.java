package com.example.json.service;

import com.example.json.data.dtos.exports.CategoryByProductsCountDto;

import java.io.FileNotFoundException;
import java.util.Set;

public interface CategoryService {

    void seedCategory() throws FileNotFoundException;

    Set<CategoryByProductsCountDto> categoriesByProductsCount();

    void printCategories();
}
