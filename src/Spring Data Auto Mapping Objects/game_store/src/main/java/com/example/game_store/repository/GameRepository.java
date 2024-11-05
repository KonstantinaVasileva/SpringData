package com.example.game_store.repository;

import com.example.game_store.data.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findGameById(long id);
    Game findByTitle(String title);
}
