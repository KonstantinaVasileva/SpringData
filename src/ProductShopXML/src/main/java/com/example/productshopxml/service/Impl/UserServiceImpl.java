package com.example.productshopxml.service.Impl;

import com.example.productshopxml.data.dto.exports.successfullysoldproducts.SoldProductDTO;
import com.example.productshopxml.data.dto.exports.successfullysoldproducts.SoldProductRootDTO;
import com.example.productshopxml.data.dto.exports.successfullysoldproducts.UserDTO;
import com.example.productshopxml.data.dto.exports.successfullysoldproducts.UserRootDTO;
import com.example.productshopxml.data.dto.imports.UserSeedDTO;
import com.example.productshopxml.data.dto.imports.UserSeedRootDTO;
import com.example.productshopxml.data.entities.User;
import com.example.productshopxml.repository.UserRepository;
import com.example.productshopxml.service.UserService;
import com.example.productshopxml.util.validator.ValidatorService;
import com.example.productshopxml.util.xml.XMLParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final String FILE_INPUT_PATH = "src/main/resources/input/users.xml";
    private final String FILE_OUTPUT_PATH = "src/main/resources/output/soldProduct.xml";

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidatorService validatorService;
    private final XMLParser xmlParser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidatorService validatorService, XMLParser xmlParser) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validatorService = validatorService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedUsers() throws JAXBException {

        if (userRepository.count() == 0) {
            UserSeedRootDTO userRootDTO = xmlParser.parse(UserSeedRootDTO.class, FILE_INPUT_PATH);
            for (UserSeedDTO userDTO : userRootDTO.getUserDTOList()) {
                if (!validatorService.isValid(userDTO)) {
                    validatorService.getViolation(userDTO).forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                User user = mapper.map(userDTO, User.class);
                userRepository.saveAndFlush(user);
            }
        }
    }

    @Override
    public void getUsersWithSuccessfullySoldProduct() throws JAXBException {
        List<UserDTO> userDTOList =
                userRepository.findAllBySoldIsNotNullAndBoughtIsNotNullOrderByLastNameAscFirstNameAsc()
                        .stream().map(u -> {
                            UserDTO userDTO = mapper.map(u, UserDTO.class);
                            List<SoldProductDTO> soldProductDTOList = u.getSold().stream()
                                    .map(s -> mapper.map(s, SoldProductDTO.class)).toList();
                            SoldProductRootDTO soldProductRootDTO = new SoldProductRootDTO();
                            soldProductRootDTO.setSoldProductDTOList(soldProductDTOList);
                            userDTO.setSoldProductRootDTO(soldProductRootDTO);
                            return userDTO;
                        })
                        .toList();

        UserRootDTO userRootDTO = new UserRootDTO();
        userRootDTO.setUserDTOList(userDTOList);

        xmlParser.exportToFile(userRootDTO, FILE_OUTPUT_PATH);
    }
}
