package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryDTO;
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
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
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
        CountryDTO[] countryDTOS = gson.fromJson(new FileReader(FILE_PATH), CountryDTO[].class);
        for (CountryDTO countryDTO : countryDTOS) {
            if (!validationUtil.isValid(countryDTO)){
                output.append("Invalid country").append(System.lineSeparator());
                continue;
            }
            Country country = mapper.map(countryDTO, Country.class);
            countryRepository.saveAndFlush(country);
            output.append(String.format("Successfully imported country %s - %s",
                    country.getCountryName(),
                    country.getCurrency()))
                    .append(System.lineSeparator());
        }
        return output.toString();
    }
}
