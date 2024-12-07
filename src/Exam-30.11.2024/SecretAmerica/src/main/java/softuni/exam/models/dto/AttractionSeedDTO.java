package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AttractionSeedDTO {
    @Expose
    @NotNull
    @Size(min = 5, max = 40)
    private String name;
    @Expose
    @NotNull
    @Size(min = 10, max = 100)
    private String description;
    @Expose
    @NotNull
    @Size(min = 3, max = 30)
    private String type;
    @Expose
    @NotNull
    @Min(0)
    private int elevation;
    @Expose
    @NotNull
    private long country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
