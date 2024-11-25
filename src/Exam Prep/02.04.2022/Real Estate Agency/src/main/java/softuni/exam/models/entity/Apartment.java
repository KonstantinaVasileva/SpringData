package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{
    @Column(name = "apartment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type apartmentType;
    @Column(nullable = false)
    private double area;
    @ManyToOne
    private Town town;

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Type getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(Type apartmentType) {
        this.apartmentType = apartmentType;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
