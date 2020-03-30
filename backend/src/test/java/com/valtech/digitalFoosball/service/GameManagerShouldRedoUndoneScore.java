package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRedoUndoneScore extends GameManagerTest {

    @Test
    void is_possible_when_a_score_has_undone() {
        super.raiseActual(1, 2, 1, 1, 2);
        gameManager.undoLastGoal();

        gameManager.redoLastGoal();

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 3, "T2", "P3", "P4", 2, 0, 0);
    }
}
