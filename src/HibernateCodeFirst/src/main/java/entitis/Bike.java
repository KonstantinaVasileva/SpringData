package entitis;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Bike extends Vehicle{
    public Bike() {
        super();
    }

    public Bike(String type) {
        super("Bike");
    }

}
