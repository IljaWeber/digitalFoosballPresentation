package com.valtech.digitalFoosball.service.adhoc;

import com.valtech.digitalFoosball.model.output.AdHocGameOutput;
import com.valtech.digitalFoosball.service.GameManagerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldUndoScore extends GameManagerTest {

    @Test
    void is_possible_if_a_goal_was_shot() {
        gameManager.initAdHocGame();
        raiseActual(1, 2, 1, 1, 1);

        gameManager.undoLastGoal();

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
    void is_not_possible_if_no_goal_was_made() {
        gameManager.initAdHocGame();

        gameManager.undoLastGoal();

        assertThat(gameManager.getDataOfAdHocGame()).extracting(
                team -> team.getTeams().get(0).getName(),
                team -> team.getTeams().get(0).getScore(),
                team -> team.getTeams().get(0).getWonRounds(),
                team -> team.getTeams().get(1).getName(),
                team -> team.getTeams().get(1).getScore(),
                team -> team.getTeams().get(1).getWonRounds(),
                AdHocGameOutput::getMatchWinner)
                .containsExactly("Orange", 0, 0, "Green", 0, 0, 0);
    }

    @Test
    void is_possible_if_win_conditions_have_been_fulfilled() {
        gameManager.initAdHocGame();
        raiseActual(1, 1, 1, 2, 2, 1, 1, 1);

        gameManager.undoLastGoal();

        assertThat(gameManager.getDataOfAdHocGame()).extracting(
                team -> team.getTeams().get(0).getName(),
                team -> team.getTeams().get(0).getScore(),
                team -> team.getTeams().get(0).getWonRounds(),
                team -> team.getTeams().get(1).getName(),
                team -> team.getTeams().get(1).getScore(),
                team -> team.getTeams().get(1).getWonRounds(),
                AdHocGameOutput::getMatchWinner)
                .containsExactly("Orange", 5, 0, "Green", 2, 0, 0);
    }
}
