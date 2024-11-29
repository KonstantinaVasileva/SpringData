package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.json.CustomerSeedDTO;
import exam.model.entity.Customer;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final String FILE_PATH = "src/main/resources/files/json/customers.json";

    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper mapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder output = new StringBuilder();
        CustomerSeedDTO[] customerSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), CustomerSeedDTO[].class);
        for (CustomerSeedDTO customerSeedDTO : customerSeedDTOS) {
            if (!validationUtil.isValid(customerSeedDTO) ||
                    customerRepository.findByEmail(customerSeedDTO.getEmail()).isPresent()) {
                output.append("Invalid Customer").append(System.lineSeparator());
                continue;
            }
            Customer customer = mapper.map(customerSeedDTO, Customer.class);
            customer.setTown(townRepository.findByName(customerSeedDTO.getTown().getName()));
            customerRepository.saveAndFlush(customer);
            output.append(String.format("Successfully imported Customer %s %s - %s\n",
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail()));
        }
        return output.toString();
    }
}
