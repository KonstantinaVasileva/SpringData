package FootballBettingDatabase;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table
public class Games extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "home_team")
    private Teams homeTeams;

    @OneToOne
    @JoinColumn(name = "away_team")
    private Teams awayTeam;

    @Column(name = "home_goals")
    private int homeGoals;

    @Column(name = "away_goals")
    private int awayGoals;

    @Column(name = "date_and_time_of_game")
    private LocalDate dateAndTimeOfGame;

    @Column(name = "home_team_win_bet_rate")
    private int homeTeamWinBetRate;

    @Column(name = "away_team_win_bet_rate")
    private int awayTeamWinBetRate;

    @Column(name = "draw_game_bet_rate")
    private int drawGameBetRate;

    @ManyToOne
    @JoinColumn(name = "round_id", referencedColumnName = "id")
    private Rounds round;

    @ManyToOne
    @JoinColumn
    private Competitions competition;

}
