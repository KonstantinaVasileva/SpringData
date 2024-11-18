package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.DeviceDTO;
import softuni.exam.models.dto.DeviceRootDTO;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.Type;
import softuni.exam.repository.DeviceRepository;
import softuni.exam.repository.SaleRepository;
import softuni.exam.service.DeviceService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final String INPUT_FILE = "src/main/resources/files/xml/devices.xml";

    private final DeviceRepository deviceRepository;
    private final ModelMapper mapper;
    private final XmlParser parser;
    private final ValidationUtil validationUtil;
    private final SaleRepository saleRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ModelMapper mapper, XmlParser parser, ValidationUtil validationUtil, SaleRepository saleRepository) {
        this.deviceRepository = deviceRepository;
        this.mapper = mapper;
        this.parser = parser;
        this.validationUtil = validationUtil;
        this.saleRepository = saleRepository;
    }

    @Override
    public boolean areImported() {
        return deviceRepository.count() > 0;
    }

    @Override
    public String readDevicesFromFile() throws IOException {
        return Files.readString(Path.of(INPUT_FILE));
    }

    @Override
    public String importDevices() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();
        if (!areImported()) {
            DeviceRootDTO deviceRootDTO = parser.fromFile(INPUT_FILE, DeviceRootDTO.class);
            for (DeviceDTO deviceDTO : deviceRootDTO.getDeviceDTOList()) {
                if (!validationUtil.isValid(deviceDTO)
                        || deviceRepository.findByBrandAndModel(deviceDTO.getBrand(), deviceDTO.getModel()).isPresent()
                        || deviceDTO.getSaleId() > saleRepository.count()) {
                    output.append("Invalid device\n");
                    continue;
                }

                Device device = mapper.map(deviceDTO, Device.class);
                Optional<Sale> sale = saleRepository.findById(deviceDTO.getSaleId());
                device.setSale(sale.get());
                Type type = (deviceDTO.getDeviceType());
                device.setDeviceType(type);
                deviceRepository.saveAndFlush(device);
                output.append(String.format("Successfully imported device of type %s with brand %s\n",
                        device.getDeviceType(), device.getBrand()));
            }
        }
        return String.valueOf(output);
    }

    @Override
    public String exportDevices() {
         return deviceRepository
                .findAllByDeviceTypeAndPriceLessThanAndStorageGreaterThanEqual
                        (Type.SMART_PHONE, 1000, 128)
                .stream()
                 .sorted(Comparator.comparing(Device::getBrand, String.CASE_INSENSITIVE_ORDER))
                .map(device -> String.format("Device brand: %s\n" +
                                "   *Model: %s\n" +
                                "   **Storage: %s\n" +
                                "   ***Price: %.2f\n",
                        device.getBrand(),
                        device.getModel(),
                        device.getStorage(),
                        device.getPrice()))
                 .collect(Collectors.joining());
    }
}
