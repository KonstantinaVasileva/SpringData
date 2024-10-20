package entitis;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicle")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @Column
   private String type;

    @Column
   private String model;

    @Column
   private BigDecimal price;

    @Column
   private String fuelType;

   public Vehicle() {
   }

   public Vehicle(String type) {
      this.type = type;
   }

   public Vehicle(Long id, String type, String model, BigDecimal price, String fuelType) {
      this.id = id;
      this.type = type;
      this.model = model;
      this.price = price;
      this.fuelType = fuelType;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   public BigDecimal getPrice() {
      return price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public String getFuelType() {
      return fuelType;
   }

   public void setFuelType(String fuelType) {
      this.fuelType = fuelType;
   }
}
