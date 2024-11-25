package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarSeedDTO;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.Type;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {
    private final String FILE_PATH = "src/main/resources/files/json/stars.json";

    private final StarRepository starRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ConstellationRepository constellationRepository;

    public StarServiceImpl(StarRepository starRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson, ConstellationRepository constellationRepository) {
        this.starRepository = starRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.constellationRepository = constellationRepository;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder output = new StringBuilder();
        StarSeedDTO[] starSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), StarSeedDTO[].class);
        for (StarSeedDTO starSeedDTO : starSeedDTOS) {
            if (!validationUtil.isValid(starSeedDTO) ||
            starRepository.findByName(starSeedDTO.getName()).isPresent()){
                output.append("Invalid star").append(System.lineSeparator());
                continue;
            }
            Star star = mapper.map(starSeedDTO, Star.class);
            star.setConstellation(constellationRepository.findById(starSeedDTO.getConstellation()).get());
            starRepository.saveAndFlush(star);
            output.append(String.format("Successfully imported star %s - %.2f light years\n", star.getName(), star.getLightYears()));
        }
        return output.toString();
    }

    @Override
    public String exportStars() {
        return starRepository.findAllByAstronomersIsEmptyAndStarTypeIsOrderByLightYears(Type.RED_GIANT)
                .stream().map(star -> String.format("Star: %s\n" +
                        "   *Distance: %.2f light years\n" +
                        "   **Description: %s\n" +
                        "   ***Constellation: %s\n",
                        star.getName(),
                        star.getLightYears(),
                        star.getDescription(),
                        star.getConstellation().getName()))
                .collect(Collectors.joining());
    }
}
