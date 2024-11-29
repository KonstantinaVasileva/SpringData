package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.xml.TownSeedDto;
import exam.model.dto.xml.TownSeedRootDTO;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {

    private final String FILE_PATH = "src/main/resources/files/xml/towns.xml";

    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, XmlParser xmlParser, ModelMapper mapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
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
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();
        TownSeedRootDTO townSeedRootDTO = xmlParser.fromFile(FILE_PATH, TownSeedRootDTO.class);
        for (TownSeedDto townSeedDto : townSeedRootDTO.getTownSeedDtoList()) {
            if (!validationUtil.isValid(townSeedDto)) {
                output.append("Invalid Town").append(System.lineSeparator());
                continue;
            }
            Town town = mapper.map(townSeedDto, Town.class);
            townRepository.saveAndFlush(town);
        }
        return output.toString();
    }
}
