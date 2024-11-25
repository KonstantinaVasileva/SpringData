package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stars")
public class Star extends BaseEntity{
    @Column(unique = true, nullable = false)
    public String name;
    @Column(name = "light_years", nullable = false)
    public double lightYears;
    @Column(columnDefinition = "TEXT", nullable = false)
    public String description;
    @Column(name = "star_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public Type starType;
    @ManyToOne
    private Constellation constellation;
    @OneToMany(mappedBy = "observingStar")
    private List<Astronomer> astronomers;

    public List<Astronomer> getAstronomers() {
        return astronomers;
    }

    public void setAstronomers(List<Astronomer> astronomers) {
        this.astronomers = astronomers;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLightYears() {
        return lightYears;
    }

    public void setLightYears(double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getStarType() {
        return starType;
    }

    public void setStarType(Type starType) {
        this.starType = starType;
    }
}
