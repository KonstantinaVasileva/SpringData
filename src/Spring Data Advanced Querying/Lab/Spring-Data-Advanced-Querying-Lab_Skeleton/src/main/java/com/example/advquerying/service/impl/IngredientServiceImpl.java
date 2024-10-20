package com.example.advquerying.service.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repository.IngredientRepository;
import com.example.advquerying.service.IngredientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<String> getIngredientStartWith(String letter) {
        return ingredientRepository.findAllByNameStartingWith(letter)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getIngredientFromList(List<String> names) {
        return ingredientRepository.findAllByNameInOrderByName(names)
                .stream().map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updatePrice() {
        ingredientRepository.updateAllByPrice();
    }

    @Override
    @Transactional
    public void updatePriceWithName() {
        ingredientRepository.updatePriceByGivenName(List.of("Apple", "Berry"));
    }
}
