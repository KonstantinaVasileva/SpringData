package com.example.productshopxml.service;

import jakarta.xml.bind.JAXBException;

public interface ProductService {
    void seedProducts() throws JAXBException;
    void getProductsInRange() throws JAXBException;
}
