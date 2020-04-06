package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.output.GameDataModel;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRaiseScore extends GameManagerTest {

    @Test
    void in_the_order_of_scoring() {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_TWO);

        GameDataModel gameData = gameManager.getGameData();
        assertThat(super.extractTeams(gameData)).containsExactly("T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }

    @Test
    void only_during_a_running_match() {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE);

        GameDataModel actual = gameManager.getGameData();
        assertThat(super.extractTeams(actual)).containsExactly("T1", "P1", "P2", 6, 1, "T2", "P3", "P4", 0, 0, 1, 0);
    }
}
