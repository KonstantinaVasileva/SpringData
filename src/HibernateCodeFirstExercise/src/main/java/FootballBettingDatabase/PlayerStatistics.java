package FootballBettingDatabase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class PlayerStatistics implements Serializable {
    @Id
    @ManyToOne
    public Games games;

    @Id
    @ManyToOne
    public Players players;

    @Column(name = "scored_goals")
    public int scoredGoals;

    @Column(name = "player_assists")
    public String PlayerAssists;

    @Column(name = "played_minutes_during_game")
    public int PlayedMinutesDuringGame;
}
