package com.example.bookshopsystem.service;

import com.example.bookshopsystem.entity.Author;
import com.example.bookshopsystem.entity.Book;
import com.example.bookshopsystem.entity.Category;
import com.example.bookshopsystem.entity.Enum.Restriction;
import com.example.bookshopsystem.entity.Enum.Type;
import com.example.bookshopsystem.repository.AuthorRepository;
import com.example.bookshopsystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of("src/main/resources/books.txt"))
                .forEach(row -> {
                    String[] data = row.split("\\s+");

                    Author author = authorService.getRandomAuthor();
                    Type editionType = Type.values()[Integer.parseInt(data[0])];
                    LocalDate releaseDate = LocalDate.parse(data[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(data[2]);
                    BigDecimal price = new BigDecimal(data[3]);
                    Restriction ageRestriction = Restriction
                            .values()[Integer.parseInt(data[4])];
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    Set<Category> categories = categoryService.getRandomCategories();


                    Book book = new Book(title, editionType, price, copies, releaseDate,
                            ageRestriction, author, categories);

                    bookRepository.saveAndFlush(book);
                });

    }

    @Override
    public Set<String> findAllBooksAfterYear2000() {
        return bookRepository.findBookByReleaseDateAfter(LocalDate.of(2000, 12, 31))
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> findBookByAuthorsName() {
         return bookRepository.findBooksByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc("George", "Powell")
                 .stream().map(b->String.format("%s %s %d", b.getTitle(), b.getReleaseDate(), b.getCopies()))
                 .collect(Collectors.toSet());
    }
}
