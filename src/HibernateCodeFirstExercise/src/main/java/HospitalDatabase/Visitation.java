package HospitalDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Visitation extends BaseEntity {

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToMany(mappedBy = "visitation")
    private Set<Diagnose> diagnose;

    @OneToMany(mappedBy = "visitation")
    private Set<Medicament> medicaments;
}
