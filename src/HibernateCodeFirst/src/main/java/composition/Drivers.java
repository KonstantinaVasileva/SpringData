package composition;

import entitis.Car;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Drivers {

    @Id
    @Column
    private int id;

    @Column
    private String fullName;

    @ManyToMany(mappedBy = "drivers_id")
    private List<Car> cars;

    public Drivers() {

    }


}
