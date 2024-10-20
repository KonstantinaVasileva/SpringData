package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Set<Ingredient> findAllByNameStartingWith(String letter);

    Set<Ingredient> findAllByNameInOrderByName(List<String> names);

    void deleteAllByName(String name);

    @Query("UPDATE Ingredient SET price = price * 1.10")
    @Modifying
    void updateAllByPrice();

    @Query("UPDATE Ingredient SET price = price * 1.10 WHERE name in :names")
    @Modifying
    void updatePriceByGivenName(List<String> names);
}
