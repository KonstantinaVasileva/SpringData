package composition;

import entitis.Car;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table
public class PlateNumber {

    @Id
    @Column
    private BigInteger id;

    @Column
    private String number;

    @OneToOne(mappedBy = "plateNumber")
    private Car cars;

    public PlateNumber() {
        super();
    }

    public Car getCars() {
        return cars;
    }

    public void setCars(Car cars) {
        this.cars = cars;
    }

}
