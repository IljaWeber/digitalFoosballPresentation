package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SetWinVerifierShould {
    private SetWinVerifier setWinVerifier;
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;
    private Map<Team, TeamDataModel> teams;

    @BeforeEach
    void setUp() {
        setWinVerifier = new SetWinVerifier();
        teams = new HashMap<>();

        teamOne = new TeamDataModel();
        teamTwo = new TeamDataModel();

        teams.put(Team.ONE, teamOne);
        teams.put(Team.TWO, teamTwo);
    }

    @Test
    public void show_no_winner_when_no_team_scored_six_goals() {
        boolean actual = setWinVerifier.teamWon(teams, Team.ONE);

        assertThat(actual).isEqualTo(false);
    }

    @Test
    void show_no_winner_when_the_score_difference_is_less_than_two() {
        countGoalsFor(Team.ONE, Team.ONE, Team.ONE, Team.ONE, Team.ONE);
        countGoalsFor(Team.TWO, Team.TWO, Team.TWO, Team.TWO, Team.TWO, Team.TWO);

        boolean actual = setWinVerifier.teamWon(teams, Team.ONE);

        assertThat(actual).isEqualTo(false);
    }

    @Test
    public void show_that_the_last_scoring_team_won_when_they_scored_at_least_six_goals_with_a_lead_of_two() {
        countGoalsFor(Team.TWO, Team.TWO, Team.TWO, Team.TWO, Team.TWO, Team.TWO);

        boolean actual = setWinVerifier.teamWon(teams, Team.TWO);

        assertThat(actual).isEqualTo(true);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            TeamDataModel teamDataModel = this.teams.get(team);
            teamDataModel.countGoal();
        }
    }
}
