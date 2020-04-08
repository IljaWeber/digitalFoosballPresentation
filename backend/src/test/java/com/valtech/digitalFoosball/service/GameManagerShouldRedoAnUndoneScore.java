package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Stack;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRedoAnUndoneScore extends GameManagerTest {

    @Test
    void if_a_score_has_been_undone_recently() {
        super.raiseScoreOf(TEAM_ONE);
        gameManager.undoGoal();

        gameManager.redoGoal();

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        TeamOutput team = teams.get(0);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(1);
    }

    @Test
    void only_when_a_goal_was_undid_otherwise_do_nothing() {
        gameManager.redoGoal();

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        TeamOutput teamOne = teams.get(0);
        TeamOutput teamTwo = teams.get(1);
        int actualScoreTeamOne = teamOne.getScore();
        int actualScoreTeamTwo = teamTwo.getScore();
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }
}
