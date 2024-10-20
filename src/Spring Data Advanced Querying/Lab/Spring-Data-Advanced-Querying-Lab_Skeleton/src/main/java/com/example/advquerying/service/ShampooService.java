package com.example.advquerying.service;

import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {

    List<String> getShampooBySize(Size size);

    List<String> getShampooBySizeOrId(Size size, Long id);

    List<String> getShampooByPrice(BigDecimal price);

    int countShampooByPrice(BigDecimal price);

    Set<String> getShampooByIngredient(List<String> ingredients);

    Set<String> getShampooByIngredientCount(int number);

}
