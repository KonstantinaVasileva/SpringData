package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.xml.ShopSeedDTO;
import exam.model.dto.xml.ShopSeedRootDTO;
import exam.model.entity.Shop;
import exam.repository.CustomerRepository;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ShopServiceImpl implements ShopService {

    private final String FILE_PATH = "src/main/resources/files/xml/shops.xml";

    private final ShopRepository shopRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public ShopServiceImpl(ShopRepository shopRepository, XmlParser xmlParser, ModelMapper mapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.shopRepository = shopRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();
        ShopSeedRootDTO shopSeedRootDTO = xmlParser.fromFile(FILE_PATH, ShopSeedRootDTO.class);
        for (ShopSeedDTO shopSeedDTO : shopSeedRootDTO.getShopSeedDTOList()) {
            if (!validationUtil.isValid(shopSeedDTO) ||
                    shopRepository.findByName(shopSeedDTO.getName()).isPresent()) {
                output.append("Invalid Shop").append(System.lineSeparator());
                continue;
            }
            Shop shop = mapper.map(shopSeedDTO, Shop.class);
            shop.setTown(townRepository.findByName(shopSeedDTO.getTown().getName()));
            shopRepository.saveAndFlush(shop);
            output.append(String.format("Successfully imported Shop %s - %s\n",
                    shop.getName(),
                    shop.getIncome()));
        }
        return output.toString();
    }
}
