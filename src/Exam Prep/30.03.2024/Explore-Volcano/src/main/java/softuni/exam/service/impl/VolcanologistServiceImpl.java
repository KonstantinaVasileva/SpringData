package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanologistSeedDTO;
import softuni.exam.models.dto.VolcanologistSeedRootDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private final String FILE_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser parser;
    private final VolcanoRepository volcanoRepository;

    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser parser, VolcanoRepository volcanoRepository) {
        this.volcanologistRepository = volcanologistRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.parser = parser;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        VolcanologistSeedRootDTO volcanologistSeedRootDTO = parser.fromFile(FILE_PATH, VolcanologistSeedRootDTO.class);
        for (VolcanologistSeedDTO volcanologistSeedDTO : volcanologistSeedRootDTO.getVolcanologistSeedDTOList()) {
            if (!validationUtil.isValid(volcanologistSeedDTO) ||
                    volcanologistRepository.findByFirstNameAndLastName
                            (volcanologistSeedDTO.getFirstName(), volcanologistSeedDTO.getLastName()).isPresent() ||
                    volcanoRepository.findById(volcanologistSeedDTO.getExploringVolcanoId()).isEmpty()) {
                output.append("Invalid volcanologist").append(System.lineSeparator());
                continue;
            }
            Volcanologist volcanologist = mapper.map(volcanologistSeedDTO, Volcanologist.class);
            Volcano volcano = volcanoRepository.findById(volcanologistSeedDTO.getExploringVolcanoId()).get();
            volcanologist.setVolcano(volcano);
            volcanologistRepository.saveAndFlush(volcanologist);
            output.append(String.format("Successfully imported volcanologist %s %s%n",
                    volcanologist.getFirstName(), volcanologist.getLastName()));
        }
        return output.toString();
    }
}