package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//ToDo - Implement all the methods
@Service
public class CountryServiceImpl implements CountryService {

    private final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper mapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws FileNotFoundException {
        StringBuilder output = new StringBuilder();
        CountrySeedDTO[] countrySeedDTOS = gson.fromJson(new FileReader(FILE_PATH), CountrySeedDTO[].class);
        for (CountrySeedDTO countrySeedDTO : countrySeedDTOS) {
            if (!validationUtil.isValid(countrySeedDTO)||
            countryRepository.findByName(countrySeedDTO.getName()).isPresent()){
                output.append("Invalid country").append(System.lineSeparator());
                continue;
            }
            Country country = mapper.map(countrySeedDTO, Country.class);
            countryRepository.saveAndFlush(country);
            output.append(String.format("Successfully imported country %s\n",
                    country.getName()));
        }
        return output.toString();
    }
}
