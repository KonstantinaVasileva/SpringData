package HospitalDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Patient extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column
    private String address;

    @Column
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    private String picture;

    @Column(name = "medical_insurance", nullable = false)
    private boolean medicalInsurance;

    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;

//    @ManyToMany(mappedBy = "patients")
//    private Set<Diagnose> diagnoses;
//
//    @ManyToMany(mappedBy = "patients")
//    private Set<Medicament> medicaments;

}
