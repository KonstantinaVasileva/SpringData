package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonalDateDTO;
import softuni.exam.models.dto.PersonalDateRootDTO;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private final String FILE_PATH = "src/main/resources/files/xml/personal_data.xml";

    private final PersonalDataRepository personalDataRepository;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository, ModelMapper mapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.personalDataRepository = personalDataRepository;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPersonalData() throws JAXBException {
        StringBuilder output = new StringBuilder();
        PersonalDateRootDTO personalDateRootDTO = xmlParser.fromFile(FILE_PATH, PersonalDateRootDTO.class);
        for (PersonalDateDTO personalDateDTO : personalDateRootDTO.getPersonalDateDTOList()) {
            if (!validationUtil.isValid(personalDateDTO) ||
                    personalDataRepository.findByCardNumber(personalDateDTO.getCardNumber()).isPresent()) {
                output.append("Invalid personal data").append(System.lineSeparator());
                continue;
            }
            PersonalData personalData = mapper.map(personalDateDTO, PersonalData.class);
            personalDataRepository.saveAndFlush(personalData);
            output.append(String.format("Successfully imported personal data for visitor with card number %s\n",
                    personalData.getCardNumber()));
        }
        return output.toString();
    }
}
