package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CountryDTO {
    @Expose
    @NotNull
    @Size(min = 2, max = 60)
    private String countryName;
    @Expose
    @NotNull
    @Size(min = 2, max = 20)
    private String currency;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
