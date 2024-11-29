package com.example.football.service.impl;

import com.example.football.models.dto.xml.StatSeedDTO;
import com.example.football.models.dto.xml.StatSeedRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private final String FILE_PATH = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public StatServiceImpl(StatRepository statRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException {
        StringBuilder output = new StringBuilder();
        StatSeedRootDTO statSeedRootDTO = xmlParser.fromFile(FILE_PATH, StatSeedRootDTO.class);
        for (StatSeedDTO statSeedDTO : statSeedRootDTO.getStatSeedDTOList()) {
            if (!validationUtil.isValid(statSeedDTO) ||
                    statRepository.findByPassingAndShootingAndEndurance(statSeedDTO.getPassing(),
                            statSeedDTO.getShooting(),
                            statSeedDTO.getEndurance()).isPresent()) {
                output.append("Invalid Stat").append(System.lineSeparator());
                continue;
            }
            Stat stat = mapper.map(statSeedDTO, Stat.class);
            statRepository.saveAndFlush(stat);
            output.append(String.format("Successfully imported Stat %.2f - %.2f - %.2f\n",
                    stat.getPassing(),
                    stat.getShooting(),
                    stat.getEndurance()));
        }
        return output.toString();
    }
}
