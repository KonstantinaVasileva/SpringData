package softuni.exam.models.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personal_datas")
public class PersonalData extends BaseEntity{
    @Column
    private int age;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;
    @Column
    private char gender;
    @OneToOne(mappedBy = "personalData")
    private Visitor visitor;
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}