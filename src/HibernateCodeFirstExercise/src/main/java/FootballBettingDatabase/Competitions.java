package FootballBettingDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Competitions extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn
    private CompetitionTypes type;

//    @OneToMany(mappedBy = "competition")
//    private Set<Games> games;
}
