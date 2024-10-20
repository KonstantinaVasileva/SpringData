package FootballBettingDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Towns extends BaseEntity{

    @Column
    private String name;


    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Countries country;

//    @OneToMany(mappedBy = "town")
//    private Set<Teams> teams;
}
