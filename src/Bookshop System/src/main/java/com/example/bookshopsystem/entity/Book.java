package com.example.bookshopsystem.entity;

import com.example.bookshopsystem.entity.Enum.Restriction;
import com.example.bookshopsystem.entity.Enum.Type;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity{

    public Book(String title, Type editionType, BigDecimal price, int copies, LocalDate releaseDate,
                Restriction ageRestriction, Author author, Set<Category> categories) {
        this.title = title;
        this.editionType = editionType;
        this.price = price;
        this.copies = copies;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.author = author;
        this.categories = categories;
    }

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "edition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "age_restriction")
    @Enumerated(EnumType.STRING)
    private Restriction ageRestriction;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Author author;

    @ManyToMany
    private Set<Category> categories;

}
