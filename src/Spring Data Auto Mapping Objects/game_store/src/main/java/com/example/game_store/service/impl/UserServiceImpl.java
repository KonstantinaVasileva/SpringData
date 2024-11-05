package com.example.game_store.service.impl;

import com.example.game_store.data.dto.UserLogInDTO;
import com.example.game_store.data.dto.UserRegisterDTO;
import com.example.game_store.data.entity.User;
import com.example.game_store.repository.UserRepository;
import com.example.game_store.service.UserService;
import com.example.game_store.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Setter
    @Getter
    private User loggedIn;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorService validatorService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorService validatorService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorService = validatorService;
    }


    @Override
    public String userRegister (UserRegisterDTO userRegisterDTO) {

        if (!validatorService.isValid(userRegisterDTO)){
            Set<ConstraintViolation<UserRegisterDTO>> set =
                    validatorService.validate(userRegisterDTO);
            return set.stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(System.lineSeparator()));
        }

        if (!userRegisterDTO.getConfirmPassword().equals(userRegisterDTO.getPassword())){
            return "Password did not match.";
        }

        Optional<User> optional = userRepository.findAllByEmail(userRegisterDTO.getEmail());
        if (optional.isPresent()){
            return "Email already exist";
        }

        User user = modelMapper.map(userRegisterDTO, User.class);

        if (userRepository.count()==0){
            user.setAdmin(true);
        }

        userRepository.saveAndFlush(user);

        return String.format("%s was registered", userRegisterDTO.getFullName());
    }

    @Override
    public String userLogin(UserLogInDTO userLogInDTO) {
        Optional<User> optional = userRepository.findByEmailAndPassword(userLogInDTO.getEmail(), userLogInDTO.getPassword());
        if (optional.isEmpty()){
            return "Username and password are not correct.";
        }

        setLoggedIn(optional.get());

        return String.format("Successfully logged in %s", optional.get().getFullName());
    }

    @Override
    public String userLogout() {
        if (loggedIn == null){
            return "Cannot log out. No user was logged in.";
        }

        String output = String.format("User %s successfully logged out", loggedIn.getFullName());
        loggedIn = null;
        return output;
    }

    @Override
    public User getLoggedIn() {
        return loggedIn;
    }

}
