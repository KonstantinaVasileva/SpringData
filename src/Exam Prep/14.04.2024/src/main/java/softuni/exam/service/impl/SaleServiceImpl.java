package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SaleDTO;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SaleRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final String INPUT_FILE = "src/main/resources/files/json/sales.json";

    private final SaleRepository saleRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final SellerRepository sellerRepository;

    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson, SellerRepository sellerRepository) {
        this.saleRepository = saleRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return saleRepository.count() > 0;
    }

    @Override
    public String readSalesFileContent() throws IOException {
        return Files.readString(Path.of(INPUT_FILE));
    }

    @Override
    public String importSales() throws IOException {
        StringBuilder builder = new StringBuilder();
        if (!areImported()){
            SaleDTO[] saleDTOS = gson.fromJson(new FileReader(INPUT_FILE), SaleDTO[].class);
            for (SaleDTO saleDTO : saleDTOS) {
                if (!validationUtil.isValid(saleDTO)
                || saleRepository.findByNumber(saleDTO.getNumber()).isPresent()){
                    builder.append(String.format("Invalid sale%n"));
                    continue;
                }
                Sale sale = mapper.map(saleDTO, Sale.class);
                Optional<Seller> seller = sellerRepository.findById(saleDTO.getSeller());
                sale.setSeller(seller.get());
                saleRepository.saveAndFlush(sale);
                builder.append(String.format("Successfully imported sale with number %s%n", sale.getNumber()));
            }
        }
        return builder.toString();
    }
}
