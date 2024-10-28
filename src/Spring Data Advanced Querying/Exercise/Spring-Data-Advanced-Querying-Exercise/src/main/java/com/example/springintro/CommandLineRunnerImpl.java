package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();
        Scanner scanner = new Scanner(System.in);

//        String restriction = scanner.nextLine();
//        String string = scanner.nextLine();
//        int year = Integer.parseInt(scanner.nextLine());
//        int number = Integer.parseInt(scanner.nextLine());
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
//        LocalDate date = LocalDate.parse(scanner.nextLine(), inputFormatter);
//        int number = Integer.parseInt(scanner.nextLine());
        String[] fullName = scanner.nextLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
//        printBookByAgeRestriction(restriction);
//        printBookByCopiesLessThen();
//        printBookByPrice();
//        printBookNotInYear(year);
//        printTitleEditionTypeAndPriceWithReleaseDateBefore(date);

//        printAuthorWithFirstNameEndWith(string);
//
//        printBookWithAuthorLastNameStartWith(string);
//        printNumberOfBookWithTitleLongThen(number);
//        printAuthorNameAndCopiesCount(string);
//        printBookByTitle(string);
//        printAllCopiesByReleaseDateAfter(date, number);
//        deleteBooksByCopies(number);
        printNumberOfBookByAuthor(firstName, lastName);
    }

    private void printNumberOfBookByAuthor(String firstName, String lastName) {
        System.out.println(bookService.getNumberOfBooksByAuthor(firstName, lastName));
    }

    private void deleteBooksByCopies(int number) {
        System.out.println(bookService.deleteAllBooksWithCopiesLessThen(number));
    }

    private void printAllCopiesByReleaseDateAfter(LocalDate date, int number) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter.format(date);
        System.out.println(bookService.findAllCopiesAfterReleaseDate(date, number));
    }

    private void printBookByTitle(String title) {
        Book book = bookService.findBookByTitle(title);
        System.out.printf("%s %s %s %s%n",
                book.getTitle(),
                book.getEditionType(),
                book.getAgeRestriction(),
                book.getPrice());
    }

    private void printAuthorNameAndCopiesCount(String name) {
        String[] fullName = name.split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];
        int count = bookService.countAllCopiesForGivenAuthor(firstName, lastName);
        System.out.printf("%s - %d%n", name, count);
    }

    private void printNumberOfBookWithTitleLongThen(int number) {
        System.out.println(bookService.countBookWithTitleLongThen(number));
    }

    private void printBookWithAuthorLastNameStartWith(String string) {
        authorService.getBookWithAuthorLastNameStartWith(string).forEach(System.out::println);
    }

    private void printTitleContaining(String string) {
        bookService.findTitleWithAGivenString(string).forEach(System.out::println);
    }

    private void printAuthorWithFirstNameEndWith(String endString) {
        authorService.getAllAuthorWithFirstNameEndWith(endString).forEach(System.out::println);
    }

    private void printTitleEditionTypeAndPriceWithReleaseDateBefore(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter.format(date);
        bookService.findBookTitleEditionTypeAndPriceWithReleaseDateBefore(date).forEach(System.out::println);
    }

    private void printBookNotInYear(int year) {
        bookService.findBookNotInYear(year).forEach(System.out::println);
    }

    private void printBookByPrice() {
        bookService.findBookByPrice().forEach(System.out::println);
    }

    private void printBookByCopiesLessThen() {
        bookService.findBookByEditionTypeWithCopiesLessThen().forEach(System.out::println);
    }

    private void printBookByAgeRestriction(String restriction) {
        bookService.findBookByAgeRestriction(restriction.toUpperCase())
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
