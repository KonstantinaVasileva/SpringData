package com.example.game_store.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class GameAllDTO {

    private String title;

    private BigDecimal price;

    public GameAllDTO(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s", title, price);
    }
}
