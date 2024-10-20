package com.example.bookshopsystem.service;

import com.example.bookshopsystem.entity.Author;
import com.example.bookshopsystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of("src/main/resources/authors.txt"))
                .stream()
                .filter(row->!row.isBlank())
                .forEach(row -> {
                    String[] data = row.split("\\s+");
                    String firstName = data[0];
                    String lastName = data[1];
                    Author author = new Author(firstName, lastName);
                    authorRepository.saveAndFlush(author);
                });
    }

    @Override
    public Author getRandomAuthor() {

        int i = ThreadLocalRandom.current().nextInt(1, (int) authorRepository.count());
        return authorRepository.findById(i).get();
    }

    @Override
    public List<String> findAuthorsByBooksReleaseDateAfter1990() {
        return authorRepository.findAuthorByBooksReleaseDateAfter(LocalDate.of(1990, 1, 1))
                .stream()
                .map(a->String.format("%s %s", a.getFirstName(), a.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> orderAuthorByBook() {
        return authorRepository.orderAuthorByBookCount()
                .stream()
                .map(a->String.format("%s %s - %d", a.getFirstName(), a.getLastName(), a.getBooks().size()))
                .collect(Collectors.toList());
    }

}
