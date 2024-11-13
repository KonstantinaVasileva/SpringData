package com.example.json.data.dtos.imports;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserSeedDTO implements Serializable {

    @Expose
    private String firstName;
    @Expose
    @NotNull
    @Size(min = 3)
    private String lastName;
    @Expose
    private int age;

    public UserSeedDTO(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
