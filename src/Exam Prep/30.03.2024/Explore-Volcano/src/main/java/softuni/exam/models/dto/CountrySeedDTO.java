package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class CountrySeedDTO implements Serializable {
    @Expose
    @Size(min = 3, max = 30)
    private String name;
    @Expose
    @Size(min = 3, max = 30)
    private String capital;

    public @Size(min = 3, max = 30) String getName() {
        return name;
    }

    public void setName(@Size(min = 3, max = 30) String name) {
        this.name = name;
    }

    public @Size(min = 3, max = 30) String getCapital() {
        return capital;
    }

    public void setCapital(@Size(min = 3, max = 30) String capital) {
        this.capital = capital;
    }
}
