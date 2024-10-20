package FootballBettingDatabase;

import javax.persistence.*;

@Entity
@Table
public class Position {

    @Id
    @Column(length = 2)
    private String id;

    @Column(name = "position_description ")
    private String positionDescription;

//    @OneToMany(mappedBy = "position")
//    private Set<Players> players;
}
