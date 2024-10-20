package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    Set<Shampoo> findAllBySizeOrderById(Size size);

    Set<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);

    Set<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countAllByPriceLessThan(BigDecimal price);

//    @Query("FROM Shampoo s WHERE s.ingredients in :ingredients")
//    Set<Shampoo> findAllWithIngredient(List<String> ingredients);

    Set<Shampoo> findAllByIngredientsNameIn(List<String> ingredients);

    @Query("FROM Shampoo s WHERE s.ingredients.size < :number")
    Set<Shampoo> findAllByIngredients(int number);
}
