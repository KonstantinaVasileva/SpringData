package exam.model.dto.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class LaptopSeedDTO {
    @Expose
    @NotNull
    @Size(min = 8)
    private String macAddress;
    @Expose
    @NotNull
    private double cpuSpeed;
    @Expose
    @NotNull
    @Min(8)
    @Max(128)
    private int ram;
    @Expose
    @NotNull
    @Min(128)
    @Max(1024)
    private int storage;
    @Expose
    @NotNull
    @Size(min = 10)
    private String description;
    @Expose
    @NotNull
    @Positive
    private BigDecimal price;
    @Expose
    @NotNull
    private String warrantyType;
    @Expose
    private ShopDTO shop;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopDTO getShop() {
        return shop;
    }

    public void setShop(ShopDTO shop) {
        this.shop = shop;
    }
}
