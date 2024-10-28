package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBookByAgeRestriction(String restriction) {
        return bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(restriction))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBookByEditionTypeWithCopiesLessThen() {
        return bookRepository.findAllByCopiesLessThanAndEditionTypeIs(5000, EditionType.GOLD)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findBookByPrice() {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .stream().map(book -> String.format("%s %S", book.getTitle(), book.getPrice()))
                .toList();
    }

    @Override
    public List<String> findBookNotInYear(int year) {
        return bookRepository.findAllByReleaseDateIsNot(year)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findBookTitleEditionTypeAndPriceWithReleaseDateBefore(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(book -> String.format("%s %s %s",
                        book.getTitle(),
                        book.getEditionType(),
                        book.getPrice()))
                .toList();
    }

    @Override
    public List<String> findTitleWithAGivenString(String string) {
        return bookRepository.findAllByTitleContains(string)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public int countBookWithTitleLongThen(int size) {
        return bookRepository.findAllByTitleSizeIsGreaterThan(size);
    }

    @Override
    public int countAllCopiesForGivenAuthor(String firstName, String lastName) {
        return bookRepository.findAllByAuthorFirstNameAndAuthorLastName(firstName, lastName);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    @Transactional
    public int findAllCopiesAfterReleaseDate(LocalDate date, int copies) {
        bookRepository.increaseCopiesOfAllBooksByReleaseDay(date, copies);
        return bookRepository.findAllCopiesByReleaseDate(date);
    }

    @Override
    @Transactional
    public int deleteAllBooksWithCopiesLessThen(int copies) {
        return bookRepository.deleteAllByCopiesIsLessThan(copies);
    }

    @Override
    public int getNumberOfBooksByAuthor(String first_name, String last_name) {
        return bookRepository.udp_num_fo_book(first_name, last_name);
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
