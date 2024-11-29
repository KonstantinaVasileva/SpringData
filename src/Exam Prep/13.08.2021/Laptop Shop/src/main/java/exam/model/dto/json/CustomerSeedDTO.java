package exam.model.dto.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CustomerSeedDTO {
    @Expose
    @Size(min = 2)
    @NotNull
    private String firstName;
    @Expose
    @Size(min = 2)
    @NotNull
    private String lastName;
    @Expose
    @NotNull
    @Email
    private String email;
    @Expose
    @NotNull
    private String registeredOn;
    @Expose
    private TownDTO town;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotNull String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(@NotNull String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public TownDTO getTown() {
        return town;
    }

    public void setTown(TownDTO town) {
        this.town = town;
    }
}
