package entitis;

import composition.Companies;

import javax.persistence.*;

@Entity
@Table
public class Plane extends Vehicle{


    @Column
    private Integer passengerCapacity;

    @ManyToOne
    //@JoinColumn(name = "company_id", referencedColumnName = "id")
    private Companies company;

    public Plane() {
        super();
    }

    public Plane(String type) {
        super("Plane");
    }
}
