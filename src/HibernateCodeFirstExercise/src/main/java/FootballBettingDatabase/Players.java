package FootballBettingDatabase;

import javax.persistence.*;

@Entity
@Table
public class Players extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(name = "squad_number")
    private int squadNumber;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Teams Team;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;

    @Column(name = "is_currently_injured")
    private Boolean IsCurrentlyInjured;
}
