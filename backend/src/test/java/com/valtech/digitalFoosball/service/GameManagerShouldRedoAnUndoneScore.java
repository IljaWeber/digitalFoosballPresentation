package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRedoAnUndoneScore extends GameManagerTest {

    GameDataExtractor dataExtractor = new GameDataExtractor();

    @Test
    void if_a_score_has_been_undone_recently() {
        super.raiseScoreOf(TEAM_ONE);
        gameManager.undoGoal();

        gameManager.redoGoal();

        dataExtractor.setGameManager(gameManager);
        int actual = dataExtractor.extractScoreOf(TEAM_ONE);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    void only_when_a_goal_was_undid_otherwise_do_nothing() {
        gameManager.redoGoal();

        dataExtractor.setGameManager(gameManager);
        int actualScoreTeamOne = dataExtractor.extractScoreOf(TEAM_ONE);
        int actualScoreTeamTwo = dataExtractor.extractScoreOf(TEAM_TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }
}
