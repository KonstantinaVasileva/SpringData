package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.dto.PictureSeedDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidatorUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private final String FILE_PATH = "src/main/resources/files/pictures.json";

    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder output = new StringBuilder();
        PictureSeedDTO[] pictureSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), PictureSeedDTO[].class);
        for (PictureSeedDTO pictureSeedDTO : pictureSeedDTOS) {
            if (!validatorUtil.isValid(pictureSeedDTO) ||
                    pictureRepository.findByPath(pictureSeedDTO.getPath()).isPresent()) {
                output.append("Invalid Picture").append(System.lineSeparator());
                continue;
            }
            Picture picture = mapper.map(pictureSeedDTO, Picture.class);
            pictureRepository.saveAndFlush(picture);
            output.append(String.format("Successfully imported Picture, with size %.2f\n",
                    picture.getSize()));
        }
        return output.toString();
    }

    @Override
    public String exportPictures() {
        return pictureRepository.findAllBySizeGreaterThanOrderBySizeAsc(30000)
                .stream().map(p -> String.format("%.2f - %s",
                        p.getSize(),
                        p.getPath()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
