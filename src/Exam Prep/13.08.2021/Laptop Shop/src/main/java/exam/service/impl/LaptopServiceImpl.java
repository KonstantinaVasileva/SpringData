package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.json.LaptopSeedDTO;
import exam.model.entity.Laptop;
import exam.model.entity.Type;
import exam.repository.CustomerRepository;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final String FILE_PATH = "src/main/resources/files/json/laptops.json";

    private final LaptopRepository laptopRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final ShopRepository shopRepository;

    public LaptopServiceImpl(LaptopRepository laptopRepository, Gson gson, ModelMapper mapper, ValidationUtil validationUtil, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.shopRepository = shopRepository;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder output = new StringBuilder();
        LaptopSeedDTO[] laptopSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), LaptopSeedDTO[].class);
        for (LaptopSeedDTO laptopSeedDTO : laptopSeedDTOS) {
            Type type;
            try {
                type = Type.valueOf(laptopSeedDTO.getWarrantyType());
            } catch (IllegalArgumentException e) {
                type = null;
            }
            if (!validationUtil.isValid(laptopSeedDTO) ||
                    laptopRepository.findByMacAddress(laptopSeedDTO.getMacAddress()).isPresent() ||
                    type == null) {
                output.append("Invalid Laptop").append(System.lineSeparator());
                continue;
            }
            Laptop laptop = mapper.map(laptopSeedDTO, Laptop.class);
            laptop.setShop(shopRepository.findByName(laptopSeedDTO.getShop().getName()).get());
            laptop.setWarrantyType(type);
            laptopRepository.saveAndFlush(laptop);
            output.append(String.format("Successfully imported Laptop %s - %.2f - %s - %s",
                    laptop.getMacAddress(),
                    laptop.getCpuSpeed(),
                    laptop.getRam(),
                    laptop.getStorage()));
        }
        return output.toString();
    }

    @Override
    public String exportBestLaptops() {
        return laptopRepository.getAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc()
                .stream().map(l -> String.format("""
                                Laptop - %s
                                *Cpu speed - %.2f
                                **Ram - %s
                                ***Storage - %s
                                ****Price - %.2f
                                #Shop name - %s
                                ##Town - %s
                                """,
                        l.getMacAddress(),
                        l.getCpuSpeed(),
                        l.getRam(),
                        l.getStorage(),
                        l.getPrice(),
                        l.getShop().getName(),
                        l.getShop().getTown().getName()))
                .collect(Collectors.joining());
    }
}
