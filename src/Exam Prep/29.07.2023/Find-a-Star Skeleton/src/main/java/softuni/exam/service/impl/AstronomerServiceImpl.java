package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomersDTO;
import softuni.exam.models.dto.AstronomersRootDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AstronomerServiceImpl implements AstronomerService {

    private final String FILE_PATH = "src/main/resources/files/xml/astronomers.xml";

    private final AstronomerRepository astronomerRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final StarRepository starRepository;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, StarRepository starRepository) {
        this.astronomerRepository = astronomerRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.starRepository = starRepository;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        AstronomersRootDTO astronomersRootDTO = xmlParser.fromFile(FILE_PATH, AstronomersRootDTO.class);
        for (AstronomersDTO astronomersDTO : astronomersRootDTO.getAstronomersDTOList()) {
            if (!validationUtil.isValid(astronomersDTO) ||
            astronomerRepository.findByFirstNameAndLastName(astronomersDTO.getFirstName(), astronomersDTO.getLastName()).isPresent()||
            starRepository.findById(astronomersDTO.getObservingStarId()).isEmpty()){
                output.append("Invalid astronomer").append(System.lineSeparator());
                continue;
            }
            Astronomer astronomer = mapper.map(astronomersDTO, Astronomer.class);
            Star star = starRepository.findById(astronomersDTO.getObservingStarId()).get();
            astronomer.setObservingStar(star);
            astronomerRepository.saveAndFlush(astronomer);
            output.append(String.format("Successfully imported astronomer %s %s - %.2f\n",
                    astronomer.getFirstName(),
                    astronomer.getLastName(),
                    astronomer.getAverageObservationHours()));
        }
        return output.toString();
    }
}
