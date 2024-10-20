package FootballBettingDatabase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Rounds extends BaseEntity{

    @Column(nullable = false)
    private String name;

//    @OneToMany(mappedBy = "round")
//    private Set<Games> games;
}
