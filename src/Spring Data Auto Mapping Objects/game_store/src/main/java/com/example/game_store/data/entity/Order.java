package com.example.game_store.data.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @OneToOne
    private User user;

    @ManyToMany
    private Set<Game> games;

    public Order() {
        this.games = new HashSet<>();
        this.user = new User();
    }

    public Order(User user, Set<Game> games) {
        this.user = user;
        this.games = games;
    }
}
