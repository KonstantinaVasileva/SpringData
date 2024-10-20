package FootballBettingDatabase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
public class Bets extends BaseEntity{

    @Column
    private BigDecimal money;

    @Column(name = "date_and_time")
    private LocalDate dateAndTime;

    @ManyToOne
    //@JoinColumn(name = "User_id", referencedColumnName = "id")
    private Users user;


}
