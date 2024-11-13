package com.example.json.data.dtos.exports;

import com.google.gson.annotations.Expose;
import lombok.Setter;

@Setter
public class UserDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private CountSoldProductDto soldProducts;
}
