package com.valtech.digitalFoosball.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRaiseScore extends GameManagerTest {

    @Test
    void in_the_order_of_scoring() {
        super.raiseActual(1, 1, 1, 2);

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }

    @Test
    void but_if_a_team_has_fulfilled_win_conditions_then_do_nothing() {
        super.raiseActual(1, 1, 1, 1, 1, 1);

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 6, 1, "T2", "P3", "P4", 0, 0, 1, 0);
    }
}
