package com.example.game_store.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class GameDetailsDTO {

    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return "Title: " + title + "%n" +
                ", price: " + price + "%n" +
                ", description: " + description + "%n" +
                ", releaseDate: " + releaseDate ;
    }
}
