package com.valtech.digitalFoosball.domain.game.verifier;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.Team.NO_TEAM;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameRulesShould {
    private RankedGameRules setWinVerifier;
    private RankedTeamDataModel teamOne;
    private RankedTeamDataModel teamTwo;
    private List<RankedTeamDataModel> teams;
    private RankedGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        setWinVerifier = new RankedGameRules();
        teams = new ArrayList<>();

        teamOne = new RankedTeamDataModel();
        teamTwo = new RankedTeamDataModel();

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel = new RankedGameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    public void show_no_winner_when_no_team_scored_six_goals() {
        setWinVerifier.approveWin(gameDataModel);
        Team actual = gameDataModel.getSetWinner();

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    void show_no_winner_when_the_score_difference_is_less_than_two() {
        countGoalsFor(Team.ONE, Team.ONE, Team.ONE, Team.ONE, Team.ONE);
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        setWinVerifier.approveWin(gameDataModel);
        Team actual = gameDataModel.getSetWinner();

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    public void show_that_the_last_scoring_team_won_when_they_scored_at_least_six_goals_with_a_lead_of_two() {
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        setWinVerifier.approveWin(gameDataModel);
        Team actual = gameDataModel.getSetWinner();

        assertThat(actual).isEqualTo(TWO);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            RankedTeamDataModel teamDataModel = gameDataModel.getTeam(team);
            teamDataModel.countGoal();
        }
    }
}
