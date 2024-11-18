package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "devices")
public class Device extends BaseEntity{
    @Column(nullable = false)
    private String brand;
    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private Type deviceType;
    @Column(unique = true, nullable = false)
    private String model;
    @Column
    private double price;
    @Column
    private int storage;
    @ManyToOne
    private Sale sale;

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Type getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Type deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }
}
