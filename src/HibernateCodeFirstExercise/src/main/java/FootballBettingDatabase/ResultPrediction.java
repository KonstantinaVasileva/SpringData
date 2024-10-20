package FootballBettingDatabase;

import javax.persistence.*;

@Entity
@Table
public class ResultPrediction extends BaseEntity{

    @Column
    @Enumerated(EnumType.STRING)
    private Prediction prediction;
}
