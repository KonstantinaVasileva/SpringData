package com.example.advquerying.service.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.service.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> getShampooBySize(Size size) {
        return shampooRepository.findAllBySizeOrderById(size)
                .stream()
                .map(s -> String.format("%s %s %slv.", s.getBrand(), s.getSize(), s.getPrice()))
                .collect(Collectors.toList());

    }

    @Override
    public List<String> getShampooBySizeOrId(Size size, Long id) {
        return shampooRepository.findAllBySizeOrLabelIdOrderByPrice(size, id)
                .stream()
                .map(s -> String.format("%s %s %slv.", s.getBrand(), s.getSize(), s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getShampooByPrice(BigDecimal price) {
        return shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(s -> String.format("%s %s %slv.", s.getBrand(), s.getSize(), s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public int countShampooByPrice(BigDecimal price) {
        return shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public Set<String> getShampooByIngredient(List<String> ingredients) {
        return shampooRepository.findAllByIngredientsNameIn(ingredients)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getShampooByIngredientCount(int number) {
        return shampooRepository.findAllByIngredients(number)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toSet());
    }
}
