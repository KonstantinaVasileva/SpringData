package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        if (user.getUsername().isBlank() || user.getUsername().isEmpty()||user.getAge() < 18){
            throw new RuntimeException("Not valid user");
        }
        Optional<User> byUsername = userRepository.getByUsername(user.getUsername());
        if (byUsername.isPresent()){
            throw new RuntimeException("Username already exist");
        }



        userRepository.saveAndFlush(user);
    }

}
