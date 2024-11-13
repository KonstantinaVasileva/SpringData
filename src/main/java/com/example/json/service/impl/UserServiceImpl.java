package com.example.json.service.impl;

import com.example.json.data.dtos.exports.*;
import com.example.json.data.dtos.imports.UserSeedDTO;
import com.example.json.data.entities.User;
import com.example.json.repository.UserRepository;
import com.example.json.service.UserService;
import com.example.json.util.ValidatorService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final ValidatorService validator;
    private final Gson gson;

    public UserServiceImpl(ModelMapper mapper, UserRepository userRepository, ValidatorService validator, Gson gson) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public void seedUser() throws FileNotFoundException {
        if (userRepository.count() == 0) {
            UserSeedDTO[] userSeedDTOs = gson.fromJson(new FileReader
                    ("src/main/resources/json/users.json"), UserSeedDTO[].class);
            for (UserSeedDTO userSeedDTO : userSeedDTOs) {
                if (!validator.isValid(userSeedDTO)) {
                    validator.getViolation(userSeedDTO)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                userRepository.saveAndFlush(mapper.map(userSeedDTO, User.class));
            }
        }
    }

    @Override
    public Set<UserSoldDto> soldAtLeastOneProductWithBuyer() {
        Set<User> users = userRepository.findAllBySoldBuyerIsNotNull()
                .stream().filter(user -> !user.getSold().isEmpty())
                .collect(Collectors.toSet());

        return users.stream()
                .map(user -> {
                    UserSoldDto userSoldDto = mapper.map(user, UserSoldDto.class);

                    Set<SoldProductDto> soldProductDtos = user.getSold().stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(s -> mapper.map(s, SoldProductDto.class))
                            .collect(Collectors.toSet());
                    userSoldDto.setSoldProduct(soldProductDtos);
                    return userSoldDto;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public void printUserAndSoldProduct() {
        System.out.println(gson.toJson(soldAtLeastOneProductWithBuyer()));
    }

    @Override
    public UserCountDto getAllUsersWithSoldProducts() {
        int userCount = userRepository.findAllBySoldIsNotNull().size();
        UserCountDto userCountDto = new UserCountDto();
        userCountDto.setCount(userCount);
        userRepository.findAllBySoldIsNotNull()
                .forEach(user -> {

                    int productCount = user.getSold().size();
                    CountSoldProductDto countSoldProductDto = new CountSoldProductDto();
                    user.getSold()
                            .forEach(product ->
                            {
                                ProductDto productDto = mapper.map(product, ProductDto.class);

                                countSoldProductDto.setCount(productCount);
                                countSoldProductDto.getProducts().add(productDto);

                            });
                    UserDto userDto = mapper.map(user, UserDto.class);
                    userDto.setSoldProducts(countSoldProductDto);
                    userCountDto.getUsers().add(userDto);
                });
        return userCountDto;
    }

    @Override
    public void printUserAndProduct(){
        System.out.println(gson.toJson(getAllUsersWithSoldProducts()));
    }
}
