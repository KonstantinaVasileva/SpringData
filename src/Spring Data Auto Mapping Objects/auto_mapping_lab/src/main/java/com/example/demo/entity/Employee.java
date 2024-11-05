package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Employee {

    @Id
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private BigDecimal salary;

    @Column
    private LocalDate birthday;

    @Column
    private String address;

    @Enumerated
    @Column
    private isOnWork isOnWork;

    @ManyToOne
    private Employee manager;

    @OneToMany
    private List<Employee> employees;

    public Employee() {
        this.employees = new ArrayList<>();
    }
}
