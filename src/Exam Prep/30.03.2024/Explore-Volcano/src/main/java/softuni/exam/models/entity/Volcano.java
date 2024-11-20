package softuni.exam.models.entity;

import softuni.exam.models.enums.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "volcanoes")
public class Volcano extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int elevation;
    @Column(name = "volcano_type")
    @Enumerated(value = EnumType.STRING)
    private Type volcanoType;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "last_eruption")
    private LocalDate lastEruption;
    @ManyToOne
    private Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public Type getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(Type volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }
}
