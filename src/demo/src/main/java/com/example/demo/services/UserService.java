package com.example.demo.services;

import com.example.demo.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(User user);

}
