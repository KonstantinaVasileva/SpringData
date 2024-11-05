package com.example.game_store;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper (){
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }
}
