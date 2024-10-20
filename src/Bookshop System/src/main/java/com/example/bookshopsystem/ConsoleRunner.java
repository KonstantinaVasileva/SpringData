package com.example.bookshopsystem;

import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    public ConsoleRunner(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedDatabase();

        //findBookAfter();

        //findAuthorsByReleaseDate();

        //orderAuthorByBookCount();

        getBooksFromAuthor();
    }

    private void getBooksFromAuthor() {
        bookService.findBookByAuthorsName()
                .forEach(System.out::println);
    }

    private void findBookAfter() {
        bookService.findAllBooksAfterYear2000()
                .forEach(System.out::println);
    }

    private void orderAuthorByBookCount() {
        authorService.orderAuthorByBook()
                .forEach(System.out::println);
    }

    private void findAuthorsByReleaseDate() {
        authorService.findAuthorsByBooksReleaseDateAfter1990()
                .forEach(System.out::println);
    }

    private void seedDatabase() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
