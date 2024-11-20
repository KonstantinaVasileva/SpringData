package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CountryServiceImpl implements CountryService {

    private final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtil validationUtil, ModelMapper mapper, Gson gson) {
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder output = new StringBuilder();
        CountrySeedDTO[] countrySeedDTOS = gson.fromJson(new FileReader(FILE_PATH), CountrySeedDTO[].class);
        for (CountrySeedDTO countrySeedDTO : countrySeedDTOS) {
            if (!validationUtil.isValid(countrySeedDTO) ||
                    countryRepository.findByName(countrySeedDTO.getName()).isPresent()) {
                output.append("Invalid country").append(System.lineSeparator());
                continue;
            }
            Country country = mapper.map(countrySeedDTO, Country.class);
            countryRepository.saveAndFlush(country);
            output.append(String.format("Successfully imported country %s - %s%n",
                    country.getName(), country.getCapital()));
        }
        return output.toString();
    }
}
