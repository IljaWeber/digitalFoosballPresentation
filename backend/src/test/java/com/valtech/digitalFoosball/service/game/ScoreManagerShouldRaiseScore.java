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

public class ScoreManagerShouldRaiseScore {

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

        gameDataModel = new GameDataModel(teams);
    }

    @Test
    void in_the_order_of_scoring() {
        raiseScoreOf(ONE, ONE, ONE, TWO);

        int scoreOfTeamOne = getScoreOfTeam(ONE);
        int scoreOfTeamTwo = getScoreOfTeam(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(3);
        assertThat(scoreOfTeamTwo).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        TeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getScore();
    }

    @Test
    void only_until_the_win_condition_is_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(6);
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            scoreManager.countGoalFor(team, gameDataModel);
        }
    }
}