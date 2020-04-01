package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GameManagerShouldUndoLastGoal extends GameManagerTest {

    @Test
    void is_possible_if_a_goal_was_shot() {
        gameManager.initGame(initDataModel);
        raiseActual(1, 2, 1, 1, 1);

        gameManager.undoLastGoal();

        assertThat(extractTeams(gameManager.getGameData())).containsExactly(
                "T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }

    @Test
    void is_not_possible_if_no_goal_was_made() {
        gameManager.initGame(initDataModel);

        gameManager.undoLastGoal();

        assertThat(extractTeams(gameManager.getGameData())).containsExactly(
                "T1", "P1", "P2", 0, 0, "T2", "P3", "P4", 0, 0, 0, 0);
    }

    @Test
    void is_possible_if_win_conditions_have_been_fulfilled() {
        gameManager.initGame(initDataModel);
        raiseActual(1, 1, 1, 2, 2, 1, 1, 1);

        gameManager.undoLastGoal();

        assertThat(extractTeams(gameManager.getGameData())).containsExactly(
                "T1", "P1", "P2", 5, 0, "T2", "P3", "P4", 2, 0, 0, 0);
    }
}
