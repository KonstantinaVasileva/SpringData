package FootballBettingDatabase;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Countries {
    @Id
    @Column(length = 3)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    @ManyToMany
    @JoinTable(name = "continents_country",
    joinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
    private Set<Continents> continents;

//    @OneToMany(mappedBy = "country")
//    private Set<Towns> towns;
}
