package com.example.bookshopsystem.service;

import com.example.bookshopsystem.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> findAuthorsByBooksReleaseDateAfter1990();

    List<String> orderAuthorByBook();

}
