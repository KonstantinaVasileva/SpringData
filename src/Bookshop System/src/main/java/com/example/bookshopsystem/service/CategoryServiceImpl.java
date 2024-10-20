package com.example.bookshopsystem.service;

import com.example.bookshopsystem.entity.Category;
import com.example.bookshopsystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of("src/main/resources/categories.txt"))
                .stream()
                .filter(row -> !row.isBlank())
                .forEach(row -> {
                    Category category = new Category(row);
                    categoryRepository.saveAndFlush(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        int i = ThreadLocalRandom.current().nextInt(1, (int) categoryRepository.count());

        List<Category> allById = categoryRepository.findAllById(Collections.singleton(i));
        return new HashSet<>(allById);
    }
}
