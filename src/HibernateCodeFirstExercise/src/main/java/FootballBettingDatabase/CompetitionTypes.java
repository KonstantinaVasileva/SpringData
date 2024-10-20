package FootballBettingDatabase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class CompetitionTypes extends BaseEntity{

    @Column(nullable = false)
    private String name;
}
