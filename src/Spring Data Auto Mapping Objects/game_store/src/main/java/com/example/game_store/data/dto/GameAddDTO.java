package com.example.game_store.data.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GameAddDTO {

    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,100}$", message = "Incorrect title.")
    private String title;

    @Positive
    private BigDecimal price;

    @Positive
    private double size;

    @Size(min = 11, max = 11, message = "Incorrect trailer.")
    private String trailer;

    @Pattern(regexp = "^(?:http://)*(?:https://)*.+",
            message = "Thumbnail dose not start with the correct protocol.")
    private String thumbnail;

    @Size(min = 20, message = "Description must be at least 20 symbol.")
    private String description;

    private LocalDate releaseDate;

    public GameAddDTO(String title, BigDecimal price, double size, String trailer, String thumbnail, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnail = thumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
    }
}
