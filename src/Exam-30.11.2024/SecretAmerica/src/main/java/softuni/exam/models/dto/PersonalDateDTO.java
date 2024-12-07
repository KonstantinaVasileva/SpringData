package softuni.exam.models.dto;

import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement(name = "personal_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDateDTO {
    @XmlElement
    @Min(1)
    @Max(100)
    private int age;
    @XmlElement
    private char gender;
    @XmlElement(name = "birth_date")
    private String birthDate;
    @XmlElement(name = "card_number")
    @NotNull
    @Size(min = 9, max = 9)
    private String cardNumber;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
