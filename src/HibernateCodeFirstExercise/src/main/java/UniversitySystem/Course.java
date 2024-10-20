package UniversitySystem;

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
public class Course extends BaseEntity{

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column
    private int credits;

    @ManyToMany
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Set<Student> students;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
}
