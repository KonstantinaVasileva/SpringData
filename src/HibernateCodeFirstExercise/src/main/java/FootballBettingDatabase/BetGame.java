package FootballBettingDatabase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class BetGame implements Serializable {
    @Id
    @OneToOne
    private Games game;

    @Id
    @OneToOne
    private Bets bet;

    @OneToOne
    @JoinColumn(name = "result_prediction", nullable = false)
    private ResultPrediction  resultPrediction;
}
