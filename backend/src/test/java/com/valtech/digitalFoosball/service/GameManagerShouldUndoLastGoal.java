package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class GameManagerShouldUndoLastGoal extends GameManagerTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    void is_possible_if_a_goal_was_shot() {
        gameManager.initGame(initDataModel);
        raiseActual(1, 2, 1, 1, 1);

        gameManager.undoLastGoal();

        assertThat(extractTeams(gameManager.getGameData())).containsExactly(
                "T1", "P1", "P2", 3, "T2", "P3", "P4", 1, 0, 0);
    }


    private void raiseActual(int... teams) {

        for (int team : teams) {
            gameManager.raiseScore(team);
        }
    }

    private List extractTeams(GameDataModel gameDataModel) {
        List<TeamOutput> list = gameDataModel.getTeams();
        List mergedResult = new ArrayList();

        for (TeamOutput teamOutput : list) {
            mergedResult.add(teamOutput.getName());
            mergedResult.add(teamOutput.getPlayerOne());
            mergedResult.add(teamOutput.getPlayerTwo());
            mergedResult.add(teamOutput.getScore());
        }

        mergedResult.add(gameDataModel.getRoundWinner());
        mergedResult.add(gameDataModel.getMatchWinner());

        return mergedResult;
    }
}
