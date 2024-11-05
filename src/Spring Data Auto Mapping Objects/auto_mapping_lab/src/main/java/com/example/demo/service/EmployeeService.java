package com.example.demo.service;

import com.example.demo.entity.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface EmployeeService {

    List<Employee> findEmployeeBornBefore(LocalDate date);
}
