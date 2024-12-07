package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VisitorsSeedDTO;
import softuni.exam.models.dto.VisitorsSeedRootDTO;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.VisitorService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final String FILE_PATH = "src/main/resources/files/xml/visitors.xml";

    private final VisitorRepository visitorRepository;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final PersonalDataRepository personalDataRepository;
    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository, ModelMapper mapper, XmlParser xmlParser, ValidationUtil validationUtil, PersonalDataRepository personalDataRepository, AttractionRepository attractionRepository, CountryRepository countryRepository) {
        this.visitorRepository = visitorRepository;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.personalDataRepository = personalDataRepository;
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return visitorRepository.count() > 0;
    }

    @Override
    public String readVisitorsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVisitors() throws JAXBException {
        StringBuilder output = new StringBuilder();
        VisitorsSeedRootDTO visitorsSeedRootDTO = xmlParser.fromFile(FILE_PATH, VisitorsSeedRootDTO.class);
        for (VisitorsSeedDTO visitorsSeedDTO : visitorsSeedRootDTO.getVisitorsSeedDTOList()) {
            if (!validationUtil.isValid(visitorsSeedDTO) ||
                    visitorRepository.findByFirstNameAndLastName(visitorsSeedDTO.getFirstName(), visitorsSeedDTO.getLastName()).isPresent() ||
                    visitorRepository.findByPersonalData(
                            personalDataRepository.findById(
                                    visitorsSeedDTO.getPersonalDataId())).isPresent()) {
                output.append("Invalid visitor").append(System.lineSeparator());
                continue;
            }
            Visitor visitor = mapper.map(visitorsSeedDTO, Visitor.class);
            visitor.setAttraction(attractionRepository.findById(visitorsSeedDTO.getAttractionId()).get());
            visitor.setCountry(countryRepository.findById(visitorsSeedDTO.getCountryId()).get());
            visitor.setPersonalData(personalDataRepository.findById(visitorsSeedDTO.getPersonalDataId()).get());
            visitorRepository.saveAndFlush(visitor);
            output.append(String.format("Successfully imported visitor %s %s\n",
                    visitor.getFirstName(),
                    visitor.getLastName()));
        }
        return output.toString();
    }
}
