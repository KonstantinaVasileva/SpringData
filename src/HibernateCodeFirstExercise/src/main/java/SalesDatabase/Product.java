package SalesDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Double quantity;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private Set<Sale> sales;

    public Product(String name, Double quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
