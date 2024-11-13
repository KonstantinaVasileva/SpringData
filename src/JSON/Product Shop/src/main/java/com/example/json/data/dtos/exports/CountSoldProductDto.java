package com.example.json.data.dtos.exports;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class CountSoldProductDto {

    @Expose
    private int count;
    @Expose
    private Set<ProductDto> products;

    public CountSoldProductDto() {
        this.products = new HashSet<>();
    }
}
