package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {

    private final String FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder output = new StringBuilder();
        TownSeedDTO[] townSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), TownSeedDTO[].class);
        for (TownSeedDTO townSeedDTO : townSeedDTOS) {
            if (!validationUtil.isValid(townSeedDTO)) {
                output.append("Invalid town").append(System.lineSeparator());
                continue;
            }
            Town town = mapper.map(townSeedDTO, Town.class);
            townRepository.saveAndFlush(town);
            output.append(String.format("Successfully imported town %s - %s\n",
                    town.getTownName(),
                    town.getPopulation()));
        }
        return output.toString();
    }
}
