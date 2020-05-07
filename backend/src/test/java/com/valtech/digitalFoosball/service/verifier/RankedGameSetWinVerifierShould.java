package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.verifier.setwin.RankedGameSetWinVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;
import static com.valtech.digitalFoosball.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameSetWinVerifierShould {
    private RankedGameSetWinVerifier setWinVerifier;
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;
    private List<TeamDataModel> teams;
    private GameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        setWinVerifier = new RankedGameSetWinVerifier();
        teams = new ArrayList<>();

        teamOne = new TeamDataModel();
        teamTwo = new TeamDataModel();

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel = new GameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    public void show_no_winner_when_no_team_scored_six_goals() {
        Team actual = setWinVerifier.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    void show_no_winner_when_the_score_difference_is_less_than_two() {
        countGoalsFor(Team.ONE, Team.ONE, Team.ONE, Team.ONE, Team.ONE);
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        Team actual = setWinVerifier.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    public void show_that_the_last_scoring_team_won_when_they_scored_at_least_six_goals_with_a_lead_of_two() {
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        Team actual = setWinVerifier.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(TWO);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            TeamDataModel teamDataModel = gameDataModel.getTeam(team);
            teamDataModel.countGoal();
        }
    }
}
