package HospitalDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Diagnose extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(name = "visitation_id", referencedColumnName = "id")
    private Visitation visitation;
}
