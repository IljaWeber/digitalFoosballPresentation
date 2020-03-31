package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRaiseScore extends GameManagerTest {

    @Test
    void is_possible_if_match_is_not_over() {
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(2);

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }

    @Test
    void is_not_possible_if_match_is_over() {
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 6, 1, "T2", "P3", "P4", 0, 0, 1, 0);
    }

    @Test
    void is_not_possible_if_a_team_has_fulfilled_the_round_win_condition() {
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(1);
        gameManager.increaseScore(2);
        gameManager.increaseScore(2);
        gameManager.increaseScore(1);

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 6, 1, "T2", "P3", "P4", 2, 0, 1, 0);
    }
}
