package com.example.productshopxml.service;

import jakarta.xml.bind.JAXBException;

public interface CategoryService {
    void seedCategory() throws JAXBException;
    void getCategoriesAndProductsCount() throws JAXBException;
}
