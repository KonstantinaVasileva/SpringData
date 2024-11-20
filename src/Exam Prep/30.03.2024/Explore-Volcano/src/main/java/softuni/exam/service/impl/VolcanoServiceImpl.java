package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoSeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private final String FILE_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final CountryRepository countryRepository;

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson, CountryRepository countryRepository) {
        this.volcanoRepository = volcanoRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder output = new StringBuilder();
        VolcanoSeedDTO[] volcanoSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), VolcanoSeedDTO[].class);
        for (VolcanoSeedDTO volcanoSeedDTO : volcanoSeedDTOS) {
            if (!validationUtil.isValid(volcanoSeedDTO) ||
                    volcanoRepository.findByName(volcanoSeedDTO.getName()).isPresent()) {
                output.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }
            Volcano volcano = mapper.map(volcanoSeedDTO, Volcano.class);
            Country country = countryRepository.getById(volcanoSeedDTO.getCountry());
            volcano.setCountry(country);
            volcanoRepository.saveAndFlush(volcano);
            output.append(String.format("Successfully imported volcano %s of type %s%n",
                    volcano.getName(), volcano.getVolcanoType()));
        }
        return output.toString();
    }

    @Override
    public String exportVolcanoes() {
        return volcanoRepository.findAllByIsActiveIsTrueAndElevationGreaterThanAndLastEruptionIsNotNull(3000)
                .stream()
                .sorted(Comparator.comparing(Volcano::getElevation).reversed())
                .map(volcano -> String.format("Volcano: %s\n" +
                                "   *Located in: %s\n" +
                                "   **Elevation: %s\n" +
                                "   ***Last eruption on: %s\n",
                        volcano.getName(),
                        volcano.getCountry().getName(),
                        volcano.getElevation(),
                        volcano.getLastEruption()))
                .collect(Collectors.joining());
    }
}