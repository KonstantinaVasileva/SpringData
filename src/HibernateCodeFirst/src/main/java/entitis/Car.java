package entitis;

import composition.Drivers;
import composition.PlateNumber;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Car extends Vehicle{

    @Column
    private int seats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    @ManyToMany
    @JoinColumn(name = "drivers_id", referencedColumnName = "id")
    private List<Drivers> drivers;


    public Car() {
        super();
    }

    public Car(String type) {
        super("Car");
    }

    public Car(Long id, String type, String model, BigDecimal price, String fuelType, Integer seats, PlateNumber plateNumber) {
        super(id, type, model, price, fuelType);
        this.seats = seats;
        this.plateNumber = plateNumber;
    }
}
