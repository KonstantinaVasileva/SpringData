package SalesDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.spi.Mapping;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "storelocation_id", referencedColumnName = "id")
    private StoreLocation storeLocation;

    @Column
    private LocalDate date;

    public Sale(Product product, Customer customer, StoreLocation storeLocation, LocalDate date) {
        this.product = product;
        this.customer = customer;
        this.storeLocation = storeLocation;
        this.date = date;
    }
}
