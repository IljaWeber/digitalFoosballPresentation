package com.valtech.digitalFoosball.service.adhoc;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.GameManagerTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class GameManagerShouldRedoAnUndoneScore extends GameManagerTest {


    @Test
    void is_possible_when_a_score_has_undone() {
        gameManager.initAdHocGame();
        super.raiseActual(1, 2, 1, 1, 2);
        gameManager.undoLastGoal();

        gameManager.redoLastGoal();

        assertThat(gameManager.getTeams()).extracting(TeamDataModel::getName, TeamDataModel::getScore, TeamDataModel::getWonRounds).containsExactly(
                tuple("Orange", 3, 0),
                tuple("Green", 2, 0));
    }

    @Test
    void save_it_to_the_match_history() throws Exception {
        gameManager.initAdHocGame();
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
    void is_not_possible_when_no_score_has_been_undone() {
        gameManager.initAdHocGame();
        super.raiseActual(1, 2, 1);

        gameManager.redoLastGoal();

        assertThat(gameManager.getTeams()).extracting(TeamDataModel::getName, TeamDataModel::getScore, TeamDataModel::getWonRounds).containsExactly(
                tuple("Orange", 2, 0),
                tuple("Green", 1, 0));
    }
}
