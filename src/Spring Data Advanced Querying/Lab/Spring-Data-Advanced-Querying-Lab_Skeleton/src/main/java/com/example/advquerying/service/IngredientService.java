package com.example.advquerying.service;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {

    List<String> getIngredientStartWith(String letter);

    List<String> getIngredientFromList(List<String> names);

    void deleteByName(String name);

    void updatePrice();

    void updatePriceWithName();
}
