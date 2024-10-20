package FootballBettingDatabase;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class Teams extends BaseEntity{

    @Column
    private String name;

    @Column
    private String logo;

    @Column(length = 3)
    private String initial;

    @ManyToOne
    @JoinColumn
    private Color primaryKitColor;

    @ManyToOne
    @JoinColumn
    private Color secondaryKitColor;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Towns town;

    @Column
    private BigDecimal budget;
}
