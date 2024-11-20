package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookSeedDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BookServiceImpl implements BookService {

    private final String FILE_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder output = new StringBuilder();
        BookSeedDTO[] bookSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), BookSeedDTO[].class);
        for (BookSeedDTO bookSeedDTO : bookSeedDTOS) {
            if (!validationUtil.isValid(bookSeedDTO)||
            bookRepository.findByTitle(bookSeedDTO.getTitle()).isPresent()){
                output.append("Invalid book").append(System.lineSeparator());
                continue;
            }
            Book book = mapper.map(bookSeedDTO, Book.class);
            bookRepository.saveAndFlush(book);
            output.append(String.format("Successfully imported book %s - %s\n", book.getAuthor(), book.getTitle()));
        }
        return output.toString();
    }
}
