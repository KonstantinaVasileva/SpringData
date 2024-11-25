package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CityServiceImpl implements CityService {

    private final String FILE_PATH = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtils;
    private final CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository, Gson gson, ModelMapper mapper, ValidationUtil validationUtils, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder output = new StringBuilder();
        CityDTO[] cityDTOS = gson.fromJson(new FileReader(FILE_PATH), CityDTO[].class);
        for (CityDTO cityDTO : cityDTOS) {
            if (!validationUtils.isValid(cityDTO) ||
            cityRepository.findByCityName(cityDTO.getCityName()).isPresent()){
                output.append("Invalid city").append(System.lineSeparator());
                continue;
            }
            City city = mapper.map(cityDTO, City.class);
            city.setCountry(countryRepository.findById(cityDTO.getCountry()).get());
            cityRepository.saveAndFlush(city);
            output.append(String.format("Successfully imported city %s - %s\n",
                    city.getCityName(),
                    city.getPopulation()));
        }
        return output.toString();
    }
}
