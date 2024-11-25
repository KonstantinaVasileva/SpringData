package softuni.exam.models.dto;

import softuni.exam.models.entity.Day;
import softuni.exam.util.TimeAdapter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastDTO {
    @XmlElement(name = "day_of_week")
    @NotNull
    private Day dayOfWeek;
    @XmlElement(name = "max_temperature")
    @NotNull
    @Min(-20)
    @Max(60)
    private double maxTemperature;
    @XmlElement(name = "min_temperature")
    @NotNull
    @Min(-50)
    @Max(40)
    private double minTemperature;
    @XmlElement
    @NotNull
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private LocalTime sunrise;
    @XmlElement
    @NotNull
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private LocalTime sunset;
    @XmlElement
    private long city;

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }
}
