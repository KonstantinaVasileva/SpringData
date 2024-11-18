package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellerDTO;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SaleRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private final String INPUT_FILE = "src/main/resources/files/json/sellers.json";

    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(INPUT_FILE));
    }

    @Override
    public String importSellers() throws IOException {
        StringBuilder builder = new StringBuilder();
        if (!areImported()){
            SellerDTO[] sellerDTO = gson.fromJson(new FileReader(INPUT_FILE), SellerDTO[].class);
            for (SellerDTO dto : sellerDTO) {
                if (!validationUtil.isValid(dto)
                        || sellerRepository.findByLastName(dto.getLastName()).isPresent()){
                    builder.append(String.format("Invalid seller%n"));
                    continue;
                }
                Seller seller = mapper.map(dto, Seller.class);
                sellerRepository.saveAndFlush(seller);
                builder.append(String.format("Successfully imported seller %s %s%n", seller.getFirstName(), seller.getLastName()));
            }
        }
        return builder.toString();
    }
}
