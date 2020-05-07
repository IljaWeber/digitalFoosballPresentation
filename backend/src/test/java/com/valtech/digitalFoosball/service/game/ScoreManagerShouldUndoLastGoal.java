package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static com.valtech.digitalFoosball.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreManagerShouldUndoLastGoal {
    public ScoreManager scoreManager = new ScoreManager();
    private GameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new GameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    void in_the_reversed_order_of_scoring() {
        raiseScoreOf(ONE, TWO, ONE);

        scoreManager.undoGoal(gameDataModel);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        TeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getScore();
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        scoreManager.undoGoal(gameDataModel);

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_decrease_the_number_of_won_sets_when_win_condition_has_been_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);

        scoreManager.undoGoal(gameDataModel);

        int actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(0);
    }

    private int getNumberOfWonSets(Team team) {
        TeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getWonSets();
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            scoreManager.countGoalFor(team, gameDataModel);
        }
    }
}
