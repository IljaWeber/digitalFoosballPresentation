package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRaiseScore extends GameManagerTest {

    @Test
    void is_possible_if_a_team_scores_a_goal() {
        super.raiseActual(1, 1, 2, 1);

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }
}
