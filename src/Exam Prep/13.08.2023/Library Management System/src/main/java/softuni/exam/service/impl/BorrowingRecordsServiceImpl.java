package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordsSeedDTO;
import softuni.exam.models.dto.BorrowingRecordsSeedRootDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @Override
    public boolean areImported() {
        return borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        BorrowingRecordsSeedRootDTO recordsSeedRootDTO =
                xmlParser.fromFile(FILE_PATH, BorrowingRecordsSeedRootDTO.class);
        for (BorrowingRecordsSeedDTO recordsSeedDTO : recordsSeedRootDTO.getBorrowingRecordsSeedDTOList()) {
            if (!validationUtil.isValid(recordsSeedDTO) ||
                    bookRepository.findByTitle(recordsSeedDTO.getBookDTO().getTitle()).isEmpty() ||
                    libraryMemberRepository.findById(recordsSeedDTO.getMemberDTO().getId()).isEmpty()) {
                output.append("Invalid borrowing record").append(System.lineSeparator());
                continue;
            }

            BorrowingRecord borrowingRecord = mapper.map(recordsSeedDTO, BorrowingRecord.class);
            borrowingRecord
                    .setBook(bookRepository
                            .findByTitle(recordsSeedDTO.getBookDTO().getTitle())
                            .get());
            borrowingRecord
                    .setLibraryMember(libraryMemberRepository
                            .findById(recordsSeedDTO.getMemberDTO().getId())
                            .get());
            borrowingRecordRepository.saveAndFlush(borrowingRecord);
            output.append(String.format("Successfully imported borrowing record %s - %s\n",
                    borrowingRecord.getBook().getTitle(),
                    borrowingRecord.getBorrowDate()));
        }
        return output.toString();
    }

    @Override
    public String exportBorrowingRecords() {
        return borrowingRecordRepository
                .findAllByBorrowDateBeforeOrderByBorrowDateDesc(LocalDate.parse("2021-09-10"))
                .stream().filter(br -> br.getBook().getGenre().equals(Genre.SCIENCE_FICTION))
                .map(r -> String.format("Book title: %s\n" +
                        "*Book author: %s\n" +
                        "**Date borrowed: %s\n" +
                        "***Borrowed by: %s %s\n",
                        r.getBook().getTitle(),
                        r.getBook().getAuthor(),
                        r.getBorrowDate(),
                        r.getLibraryMember().getFirstName(),
                        r.getLibraryMember().getLastName()
                )).collect(Collectors.joining());
    }
}
