package com.example.json.service;

import com.example.json.data.dtos.exports.UserCountDto;
import com.example.json.data.dtos.exports.UserSoldDto;

import java.io.FileNotFoundException;
import java.util.Set;

public interface UserService {

    void seedUser() throws FileNotFoundException;

    Set<UserSoldDto> soldAtLeastOneProductWithBuyer();

    void printUserAndSoldProduct();

    UserCountDto getAllUsersWithSoldProducts();

    void printUserAndProduct();

}
