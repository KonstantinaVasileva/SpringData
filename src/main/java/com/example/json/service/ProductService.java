package com.example.json.service;

import com.example.json.data.dtos.exports.ProductInRangeDto;

import java.io.FileNotFoundException;
import java.util.Set;

public interface ProductService {

    void seedProduct() throws FileNotFoundException;

    Set<ProductInRangeDto> findAllByPriceRangeAndNoBuyer();

    void printAllProducts();
}
