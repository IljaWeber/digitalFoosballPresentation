package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldRedoAnUndoneScore extends GameManagerTest {

    @Test
    void if_a_score_has_been_undone_recently() {
        super.raiseActual(1, 2, 1, 1, 2);
        gameManager.undoLastGoal();

        gameManager.redoLastGoal();

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 3, 0, "T2", "P3", "P4", 2, 0, 0, 0);
    }

    @Test
    void and_save_it_into_the_match_history() throws Exception {
        raiseActual(1, 1, 2, 1);
        gameManager.undoLastGoal();

        gameManager.redoLastGoal();

        Class cls = Class.forName("com.valtech.digitalFoosball.service.GameManager");
        Field lastScoringTeams = cls.getDeclaredField("historyOfGoals");
        lastScoringTeams.setAccessible(true);
        Stack<TeamDataModel> stack = (Stack<TeamDataModel>) lastScoringTeams.get(gameManager);
        TeamDataModel actual = stack.peek();
        assertThat(actual).isEqualTo(gameManager.getTeams().get(0));
    }

    @Test
    void only_there_is_an_undone_score_otherwise_do_nothing() {
        super.raiseActual(1, 2, 1);

        gameManager.redoLastGoal();

        assertThat(super.extractTeams(gameManager.getGameData())).containsExactly("T1", "P1", "P2", 2, 0, "T2", "P3", "P4", 1, 0, 0, 0);
    }
}
