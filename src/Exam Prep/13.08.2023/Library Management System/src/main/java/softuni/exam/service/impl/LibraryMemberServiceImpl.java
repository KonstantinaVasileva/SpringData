package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookSeedDTO;
import softuni.exam.models.dto.LibraryMemberDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private final String FILE_PATH = "src/main/resources/files/json/library-members.json";

    private final LibraryMemberRepository libraryMemberRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder output = new StringBuilder();
        LibraryMemberDTO[] libraryMemberDTOS = gson.fromJson(new FileReader(FILE_PATH), LibraryMemberDTO[].class);
        for (LibraryMemberDTO libraryMemberDTO : libraryMemberDTOS) {
            if (!validationUtil.isValid(libraryMemberDTO)||
            libraryMemberRepository.findByPhoneNumber(libraryMemberDTO.getPhoneNumber()).isPresent()){
                output.append("Invalid library member").append(System.lineSeparator());
                continue;
            }
            LibraryMember libraryMember = mapper.map(libraryMemberDTO, LibraryMember.class);
            libraryMemberRepository.saveAndFlush(libraryMember);
            output.append(String.format("Successfully imported library member %s - %s\n",
                    libraryMember.getFirstName(),
                    libraryMember.getLastName()));
        }
        return output.toString();
    }
}
