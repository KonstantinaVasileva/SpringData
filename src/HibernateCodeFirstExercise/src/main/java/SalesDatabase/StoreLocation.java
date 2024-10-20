package SalesDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class StoreLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "location_name")
    private String locationName;

    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> sales;

    public StoreLocation(String locationName) {
        this.locationName = locationName;
    }
}
