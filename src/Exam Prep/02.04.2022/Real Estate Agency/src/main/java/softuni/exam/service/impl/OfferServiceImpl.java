package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferSeedDTO;
import softuni.exam.models.dto.OfferSeedRootDTO;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Type;
import softuni.exam.models.entity.Apartment;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final String FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser parser;
    private final TownRepository townRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser parser, TownRepository townRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.parser = parser;
        this.townRepository = townRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        OfferSeedRootDTO offerSeedRootDTO = parser.fromFile(FILE_PATH, OfferSeedRootDTO.class);
        for (OfferSeedDTO offerSeedDTO : offerSeedRootDTO.getOfferSeedDTOList()) {
            if (!validationUtil.isValid(offerSeedDTO) ||
                    agentRepository.findByFirstName(offerSeedDTO.getAgent().getName()).isEmpty()) {
                output.append("Invalid offer").append(System.lineSeparator());
                continue;
            }
            Offer offer = mapper.map(offerSeedDTO, Offer.class);
            offer.setAgent(agentRepository.findByFirstName(offerSeedDTO.getAgent().getName()).get());
            offer.setApartment(apartmentRepository.findById(offerSeedDTO.getApartment().getId()).get());
            offerRepository.saveAndFlush(offer);
            output.append(String.format("Successfully imported offer %.2f\n",
                    offer.getPrice()));
        }

        return output.toString();
    }

    @Override
    public String exportOffers() {
         return offerRepository.findAllOrderByApartmentAreaAndPrice()
                .stream()
                .filter(o -> o.getApartment().getApartmentType().equals(Type.three_rooms))
                .map(offer -> String.format("Agent %s %s with offer №%s:\n" +
                                "\t\t-Apartment area: %.2f\n" +
                                "\t\t--Town: %s\n" +
                                "\t\t---Price: %.2f$\n",
                        offer.getAgent().getFirstName(),
                        offer.getAgent().getLastName(),
                        offer.getId(),
                        offer.getApartment().getArea(),
                        offer.getApartment().getTown().getTownName(),
                        offer.getPrice()))
                .collect(Collectors.joining());
    }
}
