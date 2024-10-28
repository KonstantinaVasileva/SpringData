package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesLessThanAndEditionTypeIs(int copies, EditionType editionType);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice);

    @Query("FROM Book WHERE year (releaseDate) NOT IN :year")
    List<Book> findAllByReleaseDateIsNot(int year);

    List<Book> findAllByTitleContains(String string);

    @Query("SELECT count (*) FROM Book WHERE LENGTH(title) > :size")
    int findAllByTitleSizeIsGreaterThan(int size);

    @Query("SELECT SUM(b.copies) FROM Author a " +
            "JOIN Book b on b.author = a " +
            "where a.firstName like :firstName AND a.lastName LIKE :lastName")
    int findAllByAuthorFirstNameAndAuthorLastName(String firstName, String lastName);

    Book findByTitle(String title);

    @Query("update Book SET copies = copies + :copy WHERE releaseDate > :date ")
    @Modifying
    void increaseCopiesOfAllBooksByReleaseDay(LocalDate date, int copy);

    @Query("Select SUM(copies) FROM Book WHERE releaseDate > :date")
    int findAllCopiesByReleaseDate(LocalDate date);

    @Modifying
    int deleteAllByCopiesIsLessThan(int copies);

    @Procedure
    int udp_num_fo_book(String first_name, String last_name);
}
