package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class GameManagerShouldUndoLastGoal extends GameManagerTest {

    private TeamOutput teamTwo;
    private TeamOutput teamOne;
    private List<TeamOutput> teams;
    private GameDataModel expected;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        teams = new ArrayList<>();
        teamOne = new TeamOutput();
        teamTwo = new TeamOutput();
        expected = new GameDataModel();

        teamOne.setName("T1");
        teamOne.setPlayerOne("P1");
        teamOne.setPlayerTwo("P2");

        teamTwo.setName("T2");
        teamTwo.setPlayerOne("P3");
        teamTwo.setPlayerTwo("P4");

        teams.add(teamOne);
        teams.add(teamTwo);

        expected.setTeams(teams);
    }

    @Test
    void is_possible_if_a_goal_was_shot() {
        gameManager.initGame(initDataModel);
        setExpectedScore(3, 1);
        raiseActual(1, 2, 1, 1, 1);

        gameManager.undoLastGoal();

        assertThat(extractTeams(gameManager.getGameData())).containsExactly(
                "T1", "P1", "P2", 3, "T2", "P3", "P4", 1, 0, 0);
    }

    private void setExpectedScore(int scoreOfTeamOne, int scoreOfTeamTwo) {
        teams.get(0).setScore(scoreOfTeamOne);
        teams.get(1).setScore(scoreOfTeamTwo);
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
