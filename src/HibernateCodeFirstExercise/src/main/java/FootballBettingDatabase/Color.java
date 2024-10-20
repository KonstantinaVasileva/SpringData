package FootballBettingDatabase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Color extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;
}
