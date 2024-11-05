package com.example.game_store.service;

import com.example.game_store.data.dto.CartDTO;

public interface CartService {

    String addItem(CartDTO item);

    String removeItem(CartDTO item);

    String buyItem();
}
