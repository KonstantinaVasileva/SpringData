package com.example.productshopxml.service;

import jakarta.xml.bind.JAXBException;

public interface UserService {
    void seedUsers() throws JAXBException;
    void getUsersWithSuccessfullySoldProduct() throws JAXBException;
}
