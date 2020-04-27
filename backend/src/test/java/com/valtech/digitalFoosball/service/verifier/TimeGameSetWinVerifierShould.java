package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class TimeGameSetWinVerifierShould {
    private TimeGameSetWinVerifier setWinVerifier;
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;
    private Map<Team, TeamDataModel> teams;

    @BeforeEach
    void setUp() {
        setWinVerifier = new TimeGameSetWinVerifier();
        teams = new HashMap<>();

        teamOne = new TeamDataModel();
        teamTwo = new TeamDataModel();

        teams.put(ONE, teamOne);
        teams.put(Team.TWO, teamTwo);
    }

    @Test
    public void show_no_winner_when_no_team_scored_ten_goals() {
        countGoalsFor(ONE, ONE);
        boolean actual = setWinVerifier.teamWon(teams, ONE);

        assertThat(actual).isEqualTo(false);
    }

    @Test
    public void show_winner_when_the_team_scored_ten_goals() {
        countGoalsFor(ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE);
        boolean actual = setWinVerifier.teamWon(teams, ONE);

        assertThat(actual).isEqualTo(true);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            TeamDataModel teamDataModel = this.teams.get(team);
            teamDataModel.countGoal();
        }
    }
}