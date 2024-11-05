package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.entity.dto.EmployeeDTO;
import com.example.demo.entity.dto.ManagerDTO;
import com.example.demo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final EmployeeService employeeService;

    public CommandLineRunnerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {

        ModelMapper modelMapper = new ModelMapper();

        Employee employee = new Employee();
        Employee employee2 = new Employee();

        employee.setFirstName("Pesho");
        employee.setLastName("Petrov");
        employee.setSalary(BigDecimal.valueOf(250));
        employee2.setFirstName("Vasko");
        employee2.setLastName("Jordanov");
        employee2.setSalary(BigDecimal.valueOf(450));
        List<Employee> employees = employee2.getEmployees();
        employees.add(employee);
        employee2.setEmployees(employees);

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        ManagerDTO managerDTO = modelMapper.map(employee2, ManagerDTO.class);

//        System.out.println(employeeDTO.getFirstName());
//        System.out.println(employeeDTO.getLastName());

//        System.out.printf("%s %s | Employees: %d%n%s",
//                managerDTO.getFirstName(),
//                managerDTO.getLastName(),
//                managerDTO.getEmployees().size(),
//                managerDTO.getEmployees().stream()
//                        .map(e->String.format("%s %s %s",
//                                e.getFirstName(),
//                                e.getLastName(),
//                                e.getSalary()))
//                        .collect(Collectors.joining(System.lineSeparator())));

        List<Employee> employeeBornBefore = employeeService.findEmployeeBornBefore(LocalDate.of(1990, 1, 1));

        List<EmployeeDTO> list = employeeBornBefore.stream()
                .map(e -> modelMapper.map(e, EmployeeDTO.class))
                .toList();


    }
}
