package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.models.dto.UserSeedDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidatorUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final String FILE_PATH = "src/main/resources/files/users.json";

    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final PictureRepository pictureRepository;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ModelMapper mapper, ValidatorUtil validatorUtil, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder output = new StringBuilder();
        UserSeedDTO[] userSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), UserSeedDTO[].class);
        for (UserSeedDTO userSeedDTO : userSeedDTOS) {
            if (!validatorUtil.isValid(userSeedDTO) ||
                    userRepository.findByUsername(userSeedDTO.getUsername()).isPresent() ||
                    pictureRepository.findByPath(userSeedDTO.getProfilePicture()).isEmpty()) {
                output.append("Invalid user").append(System.lineSeparator());
                continue;
            }
            User user = mapper.map(userSeedDTO, User.class);
            user.setProfilePicture(pictureRepository.findByPath(userSeedDTO.getProfilePicture()).get());
            userRepository.saveAndFlush(user);
            output.append(String.format("Successfully imported User: %s\n",
                    user.getUsername()));
        }
        return output.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return userRepository.findAllByPostsIsNotEmptyOrderByPostsDescIdAscProfilePicture()
                .stream().map(u -> String.format("""
                                User: %s
                                Post count: %s
                                %s
                                """,
                        u.getUsername(),
                        u.getPosts().size(),
                        u.getPosts()
                                .stream().map(p -> String.format("""
                                                ==Post Details:
                                                ----Caption: %s
                                                ----Picture Size: %.2f
                                                """,
                                        p.getCaption(),
                                        p.getPicture().getSize()))))
                .collect(Collectors.joining());
    }
}
