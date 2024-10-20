package entitis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Truck extends Vehicle{

    @Column
    private Double loadCapacity;

    public Truck() {
        super();
    }

    public Truck(String type) {
        super("Truck");
    }

    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
