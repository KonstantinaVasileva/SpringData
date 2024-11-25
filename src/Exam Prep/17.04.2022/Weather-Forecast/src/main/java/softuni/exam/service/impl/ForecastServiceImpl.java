package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastDTO;
import softuni.exam.models.dto.ForecastRootDTO;
import softuni.exam.models.entity.Day;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final String FILE_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CityRepository cityRepository;

    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, CityRepository cityRepository) {
        this.forecastRepository = forecastRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        ForecastRootDTO forecastRootDTO = xmlParser.fromFile(FILE_PATH, ForecastRootDTO.class);
        for (ForecastDTO forecastDTO : forecastRootDTO.getForecastDTOList()) {
            if (!validationUtil.isValid(forecastDTO) ||
            forecastRepository.findByCityAndDayOfWeek(cityRepository.findById(forecastDTO.getCity()).get(),
                    forecastDTO.getDayOfWeek()).isPresent()){
                output.append("Invalid forecast").append(System.lineSeparator());
                continue;
            }
            Forecast forecast = mapper.map(forecastDTO, Forecast.class);
            forecast.setCity(cityRepository.getById(forecastDTO.getCity()));
            forecastRepository.saveAndFlush(forecast);
            output.append(String.format("Successfully import forecast %s - %.2f",
                    forecast.getDayOfWeek(),
                    forecast.getMaxTemperature()))
                    .append(System.lineSeparator());
        }
        return output.toString();
    }

    @Override
    public String exportForecasts() {
        return forecastRepository.findAllByDayOfWeekOrderByMaxTemperatureDescIdAsc(Day.SUNDAY)
                .stream().filter(f-> f.getCity().getPopulation() < 150000)
                .map(forecast ->String.format("City: %s:\n" +
                        "   \t\t-min temperature: %.2f\n" +
                        "   \t\t--max temperature: %.2f\n" +
                        "   \t\t---sunrise: %s\n" +
                        "   \t\t----sunset: %s\n",
                        forecast.getCity().getCityName(),
                        forecast.getMinTemperature(),
                        forecast.getMaxTemperature(),
                        forecast.getSunrise(),
                        forecast.getSunset()))
                .collect(Collectors.joining());
    }
}
