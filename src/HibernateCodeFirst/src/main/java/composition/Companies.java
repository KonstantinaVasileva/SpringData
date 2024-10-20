package composition;

import entitis.Plane;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Companies {

    @Id
    @Column
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "company")
    private Set <Plane> planes;
}
