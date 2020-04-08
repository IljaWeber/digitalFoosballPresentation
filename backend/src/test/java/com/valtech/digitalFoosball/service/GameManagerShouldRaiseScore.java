package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRaiseScore extends GameManagerTest {

    GameDataExtractor dataExtractor = new GameDataExtractor();

    @Test
    void in_the_order_of_scoring() {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_TWO);

        dataExtractor.setGameManager(gameManager);
        int scoreOfTeamOne = dataExtractor.extractScoreOf(TEAM_ONE);
        int scoreOfTeamTwo = dataExtractor.extractScoreOf(TEAM_TWO);
        assertThat(scoreOfTeamOne).isEqualTo(3);
        assertThat(scoreOfTeamTwo).isEqualTo(1);
    }

    @Test
    void only_until_the_win_condition_is_fulfilled() {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE);

        dataExtractor.setGameManager(gameManager);
        int actual = dataExtractor.extractScoreOf(TEAM_ONE);
        assertThat(actual).isEqualTo(6);
    }
}
