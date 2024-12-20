package com.example.bookshopsystem.repository;

import com.example.bookshopsystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Set<Author> findAuthorByBooksReleaseDateAfter(LocalDate date);

    @Query("From Author a ORDER BY SIZE(a.books) DESC ")
    Set<Author> orderAuthorByBookCount();

}
