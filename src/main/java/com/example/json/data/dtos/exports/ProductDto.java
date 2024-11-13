package com.example.json.data.dtos.exports;

import com.google.gson.annotations.Expose;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class ProductDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
}
