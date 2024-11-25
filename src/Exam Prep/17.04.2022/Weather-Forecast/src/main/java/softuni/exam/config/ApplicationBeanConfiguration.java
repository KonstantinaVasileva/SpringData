package softuni.exam.config;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new Gson().newBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

//        modelMapper.addConverter(new Converter<String, LocalTime>() {
//            @Override
//            public LocalTime convert(MappingContext<String, LocalTime> mappingContext) {
//                LocalTime parse = LocalTime.parse(mappingContext.getSource(),
//                        DateTimeFormatter.ofPattern("HH:mm:ss"));
//                return parse;
//            }
//        });
        return modelMapper;
    }
}
