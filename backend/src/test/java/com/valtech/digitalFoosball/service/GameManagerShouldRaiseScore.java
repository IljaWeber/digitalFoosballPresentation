package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRaiseScore extends GameManagerTest {

    @Test
    void in_the_order_of_scoring() {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_TWO);

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        TeamOutput teamOne = teams.get(0);
        TeamOutput teamTwo = teams.get(1);
        assertThat(teamOne.getScore()).isEqualTo(3);
        assertThat(teamTwo.getScore()).isEqualTo(1);
    }

    @Test
    void only_until_the_win_condition_is_fulfilled() {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE);

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        TeamOutput teamOne = teams.get(0);
        int actual = teamOne.getScore();
        assertThat(actual).isEqualTo(6);
    }
}
