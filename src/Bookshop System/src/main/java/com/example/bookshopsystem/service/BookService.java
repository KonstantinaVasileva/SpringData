package com.example.bookshopsystem.service;

import java.io.IOException;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;
    Set<String> findAllBooksAfterYear2000();
    Set<String> findBookByAuthorsName();
}
