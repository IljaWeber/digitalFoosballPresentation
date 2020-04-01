package com.valtech.digitalFoosball.service.adhoc;

import com.valtech.digitalFoosball.model.output.AdHocGameOutput;
import com.valtech.digitalFoosball.service.GameManagerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GameManagerShouldRaiseScore extends GameManagerTest {

    @Test
    void is_possible_if_match_is_not_over() {
        gameManager.initAdHocGame();

        super.raiseActual(1, 1, 1, 2);

        assertThat(gameManager.getDataOfAdHocGame()).extracting(
                team -> team.getTeams().get(0).getName(),
                team -> team.getTeams().get(0).getScore(),
                team -> team.getTeams().get(0).getWonRounds(),
                team -> team.getTeams().get(1).getName(),
                team -> team.getTeams().get(1).getScore(),
                team -> team.getTeams().get(1).getWonRounds(),
                AdHocGameOutput::getMatchWinner)
                .containsExactly("Orange", 3, 0, "Green", 1, 0, 0);
    }

    @Test
    void is_not_possible_if_match_is_over() {
        gameManager.initAdHocGame();

        super.raiseActual(1, 1, 1, 1, 1, 1);

        assertThat(gameManager.getDataOfAdHocGame()).extracting(
                team -> team.getTeams().get(0).getName(),
                team -> team.getTeams().get(0).getScore(),
                team -> team.getTeams().get(0).getWonRounds(),
                team -> team.getTeams().get(1).getName(),
                team -> team.getTeams().get(1).getScore(),
                team -> team.getTeams().get(1).getWonRounds(),
                AdHocGameOutput::getMatchWinner)
                .containsExactly("Orange", 6, 1, "Green", 0, 0, 0);
    }

    @Test
    void is_not_possible_if_a_team_has_fulfilled_the_round_win_condition() {
        gameManager.initAdHocGame();

        super.raiseActual(1, 2, 2, 1, 1, 1, 1, 1, 1);

        assertThat(gameManager.getDataOfAdHocGame()).extracting(
                team -> team.getTeams().get(0).getName(),
                team -> team.getTeams().get(0).getScore(),
                team -> team.getTeams().get(0).getWonRounds(),
                team -> team.getTeams().get(1).getName(),
                team -> team.getTeams().get(1).getScore(),
                team -> team.getTeams().get(1).getWonRounds(),
                AdHocGameOutput::getMatchWinner)
                .containsExactly("Orange", 6, 1, "Green", 2, 0, 0);
    }
}

