package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final String FILE_PATH = "src/main/resources/files/json/constellations.json";

    private final ConstellationRepository constellationRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.constellationRepository = constellationRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder output = new StringBuilder();
        ConstellationSeedDTO[] constellationSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), ConstellationSeedDTO[].class);
        for (ConstellationSeedDTO constellationSeedDTO : constellationSeedDTOS) {
            if (!validationUtil.isValid(constellationSeedDTO) ||
            constellationRepository.findByName(constellationSeedDTO.getName()).isPresent()){
                output.append("Invalid constellation").append(System.lineSeparator());
                continue;
            }
            Constellation constellation = mapper.map(constellationSeedDTO, Constellation.class);
            constellationRepository.saveAndFlush(constellation);
            output.append(String.format("Successfully imported constellation %s - %s\n",
                    constellation.getName(), constellation.getDescription()));
        }
        return output.toString();
    }
}
