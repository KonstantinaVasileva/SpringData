package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AttractionSeedDTO;
import softuni.exam.models.entity.Attraction;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.AttractionService;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

//ToDo - Implement all the methods
@Service

public class AttractionServiceImpl implements AttractionService {

    private final String FILE_PATH = "src/main/resources/files/json/attractions.json";

    private final AttractionRepository attractionRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CountryRepository countryRepository;

    public AttractionServiceImpl(AttractionRepository attractionRepository, ModelMapper mapper, Gson gson, ValidationUtil validationUtil, CountryRepository countryRepository) {
        this.attractionRepository = attractionRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return attractionRepository.count() > 0;
    }

    @Override
    public String readAttractionsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAttractions() throws FileNotFoundException {
        StringBuilder output = new StringBuilder();
        AttractionSeedDTO[] attractionSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), AttractionSeedDTO[].class);
        for (AttractionSeedDTO attractionSeedDTO : attractionSeedDTOS) {
            if (!validationUtil.isValid(attractionSeedDTO) ||
                    attractionRepository.findByName(attractionSeedDTO.getName()).isPresent()) {
                output.append("Invalid attraction").append(System.lineSeparator());
                continue;
            }
            Attraction attraction = mapper.map(attractionSeedDTO, Attraction.class);
            attraction.setCountry(countryRepository.findById(attractionSeedDTO.getCountry()).get());
            attractionRepository.saveAndFlush(attraction);
            output.append(String.format("Successfully imported attraction %s\n",
                    attraction.getName()));
        }
        return output.toString();
    }

    @Override
    public String exportAttractions() {
        return attractionRepository.findAllByElevationGreaterThanEqualOrderByName()
                .stream().filter(a -> (a.getType().contains("historical") ||
                        a.getType().contains("archaeological")) &&
                        a.getElevation() >= 300)
                .map(a -> String.format("""
                                Attraction with ID%s:
                                ***%s - %s \
                                at an altitude of %sm. \
                                somewhere in %s.
                                """,
                        a.getId(),
                        a.getName(),
                        a.getDescription(),
                        a.getElevation(),
                        a.getCountry().getName()))
                .collect(Collectors.joining());
    }
}
