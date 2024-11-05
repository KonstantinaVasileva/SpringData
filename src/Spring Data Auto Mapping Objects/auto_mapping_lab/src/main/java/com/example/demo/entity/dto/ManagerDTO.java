package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ManagerDTO {

    private String firstName;

    private String lastName;

    private List<EmployeeDTO> employees;

    public ManagerDTO(String firstName, String lastName) {
        this.employees = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ManagerDTO() {
        this.employees = new ArrayList<>();
    }
}
