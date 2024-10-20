package UniversitySystem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Student extends Person{

    @Column(name = "average_grade")
    private double averageGrade;

    @Column
    private boolean attendance;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;
}
