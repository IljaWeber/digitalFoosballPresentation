package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.output.GameDataModel;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;


public class GameManagerShouldUndoLastGoal extends GameManagerTest {

    @Test
    void in_the_reversed_order_of_scoring() {
        gameManager.initGame(initDataModel);
        super.raiseScoreOf(TEAM_ONE, TEAM_TWO, TEAM_ONE, TEAM_ONE, TEAM_ONE);

        gameManager.undoLastGoal();

        GameDataModel actual = gameManager.getGameData();
        assertThat(extractTeams(actual)).containsExactly(
                "T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        gameManager.initGame(initDataModel);

        gameManager.undoLastGoal();

        GameDataModel actual = gameManager.getGameData();
        assertThat(extractTeams(actual)).containsExactly(
                "T1", "P1", "P2", 0, 0, "T2", "P3", "P4", 0, 0, 0, 0);
    }

    @Test
    void when_win_condition_has_been_fulfilled() {
        gameManager.initGame(initDataModel);
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_TWO, TEAM_TWO, TEAM_ONE, TEAM_ONE, TEAM_ONE);

        gameManager.undoLastGoal();

        GameDataModel actual = gameManager.getGameData();
        assertThat(extractTeams(actual)).containsExactly(
                "T1", "P1", "P2", 5, 0, "T2", "P3", "P4", 2, 0, 0, 0);
    }
}
