package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Stack;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.service.GameManagerTestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRedoAnUndoneScore extends GameManagerTest {

    @Test
    void if_a_score_has_been_undone_recently() {
        super.raiseScoreOf(TEAM_ONE, TEAM_TWO, TEAM_ONE, TEAM_ONE, TEAM_TWO);
        gameManager.undoGoal();

        gameManager.redoGoal();

        GameDataModel actual = gameManager.getGameData();
        assertThat(super.extractTeams(actual)).containsExactly("T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 2, 0, 0, 0);
    }

    @Test
    void and_save_it_into_the_match_history() throws Exception {
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_TWO, TEAM_ONE);
        gameManager.undoGoal();

        gameManager.redoGoal();

        Class cls = Class.forName("com.valtech.digitalFoosball.service.GameManager");
        Field lastScoringTeams = cls.getDeclaredField("historyOfGoals");
        lastScoringTeams.setAccessible(true);
        Stack<TeamDataModel> stack = (Stack<TeamDataModel>) lastScoringTeams.get(gameManager);
        TeamDataModel actual = stack.peek();
        assertThat(actual).isEqualTo(gameManager.getTeams().get(0));
    }

    @Test
    void only_there_is_an_undone_score_otherwise_do_nothing() {
        super.raiseScoreOf(TEAM_ONE, TEAM_TWO, TEAM_ONE);

        gameManager.redoGoal();

        GameDataModel actual = gameManager.getGameData();
        assertThat(super.extractTeams(actual)).containsExactly("T1", "P1", "P2", 2, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }
}
