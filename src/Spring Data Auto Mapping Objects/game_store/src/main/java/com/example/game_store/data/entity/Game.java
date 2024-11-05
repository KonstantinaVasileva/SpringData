package com.example.game_store.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "games")
@Setter
@Getter
@NoArgsConstructor
public class Game extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column
    private String trailer;

    @Column
    private String thumbnail;

    @Column
    private double size;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Double.compare(size, game.size) == 0 && Objects.equals(title, game.title) && Objects.equals(trailer, game.trailer) && Objects.equals(thumbnail, game.thumbnail) && Objects.equals(price, game.price) && Objects.equals(description, game.description) && Objects.equals(releaseDate, game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, trailer, thumbnail, size, price, description, releaseDate);
    }
}
