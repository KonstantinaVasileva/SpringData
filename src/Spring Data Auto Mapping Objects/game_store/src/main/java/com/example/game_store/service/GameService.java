package com.example.game_store.service;

import com.example.game_store.data.dto.GameAddDTO;

import java.util.Map;


public interface GameService {

    String addGame (GameAddDTO gameAddDTO);
    String editGame (Long id, Map<String, String> map);
    String deleteGame (Long id);
    String allGames();

    String detailGame(String title);

    String ownedGames();
}
