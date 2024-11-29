package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Post;
import softuni.exam.instagraphlite.models.dto.PostSeedDTO;
import softuni.exam.instagraphlite.models.dto.PostSeedRootDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidatorUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    private final String FILE_PATH = "src/main/resources/files/posts.xml";

    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PictureRepository pictureRepository, XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validatorUtil, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.userRepository = userRepository;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        PostSeedRootDTO postSeedRootDTO = xmlParser.fromFile(FILE_PATH, PostSeedRootDTO.class);
        for (PostSeedDTO postSeedDTO : postSeedRootDTO.getSeedDTOList()) {
            if (!validatorUtil.isValid(postSeedDTO) ||
                    pictureRepository.findByPath(postSeedDTO.getPictureDTO().getPath()).isEmpty() ||
                    userRepository.findByUsername(postSeedDTO.getUserDTO().getUsername()).isEmpty()) {
                output.append("Invalid Post").append(System.lineSeparator());
                continue;
            }
            Post post = mapper.map(postSeedDTO, Post.class);
            post.setPicture(pictureRepository.findByPath(postSeedDTO.getPictureDTO().getPath()).get());
            post.setUser(userRepository.findByUsername(postSeedDTO.getUserDTO().getUsername()).get());
            postRepository.saveAndFlush(post);
            output.append(String.format("Successfully imported Post, made by %s\n",
                    post.getUser().getUsername()));
        }
        return output.toString();
    }
}
