package com.example.game_store.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private String title;

    public CartDTO(String title) {
        this.title = title;
    }
}
