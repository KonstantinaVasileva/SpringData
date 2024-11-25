package softuni.exam.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "astronomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomersDTO {
    @XmlElement(name = "average_observation_hours")
    @Min(500)
    @NotNull
    private double averageObservationHours;
    @XmlElement
    private String birthday;
    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;
    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;
    @XmlElement
    @Min(1500)
    @NotNull
    private double salary;
    @XmlElement(name = "observing_star_id")
    private long observingStarId;

    public double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getObservingStarId() {
        return observingStarId;
    }

    public void setObservingStarId(long observingStarId) {
        this.observingStarId = observingStarId;
    }
}
