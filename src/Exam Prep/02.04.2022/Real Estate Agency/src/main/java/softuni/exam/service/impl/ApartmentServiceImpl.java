package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentRootSeedDTO;
import softuni.exam.models.dto.ApartmentSeedDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final String FILE_PATH = "src/main/resources/files/xml/apartments.xml";

    private final ApartmentRepository apartmentRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser parser;
    private final TownRepository townRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser parser, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.parser = parser;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        ApartmentRootSeedDTO apartmentRootSeedDTO = parser.fromFile(FILE_PATH, ApartmentRootSeedDTO.class);
        for (ApartmentSeedDTO apartmentSeedDTO : apartmentRootSeedDTO.getApartmentSeedDTOList()) {
            if (!validationUtil.isValid(apartmentSeedDTO) ||
                    apartmentRepository
                            .findByTownAndArea(townRepository
                                            .findByTownName(apartmentSeedDTO.getTown()),
                                    apartmentSeedDTO.getArea()).isPresent()) {
                output.append("Invalid apartment").append(System.lineSeparator());
                continue;
            }
            Apartment apartment = mapper.map(apartmentSeedDTO, Apartment.class);
            apartment.setTown(townRepository
                    .findByTownName(apartmentSeedDTO.getTown()));
            apartmentRepository.saveAndFlush(apartment);
            output.append(String.format("Successfully imported apartment %s - %.2f\n",
                    apartment.getApartmentType(),
                    apartment.getArea()));
        }
        return output.toString();
    }
}
