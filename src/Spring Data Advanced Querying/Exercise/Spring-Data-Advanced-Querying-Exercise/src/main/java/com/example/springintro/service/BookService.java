package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findBookByAgeRestriction(String restriction);

    List<String> findBookByEditionTypeWithCopiesLessThen();

    List<String> findBookByPrice();

    List<String> findBookNotInYear(int year);

    List<String> findBookTitleEditionTypeAndPriceWithReleaseDateBefore(LocalDate date);

    List<String> findTitleWithAGivenString(String string);

    int countBookWithTitleLongThen(int size);

    int countAllCopiesForGivenAuthor(String firstName, String lastName);

    Book findBookByTitle(String title);

    int findAllCopiesAfterReleaseDate(LocalDate date, int copies);

    int deleteAllBooksWithCopiesLessThen(int copies);

    int getNumberOfBooksByAuthor(String first_name, String last_name);
}
