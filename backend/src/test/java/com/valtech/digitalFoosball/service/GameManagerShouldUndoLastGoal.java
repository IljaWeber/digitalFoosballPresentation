package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.helper.extractor.GameDataExtractor;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.helper.constants.TestConstants.TEAM_ONE;
import static com.valtech.digitalFoosball.helper.constants.TestConstants.TEAM_TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldUndoLastGoal extends GameManagerTest {

    GameDataExtractor dataExtractor = new GameDataExtractor();

    @Test
    void in_the_reversed_order_of_scoring() {
        gameManager.initGame(initDataModel);
        super.raiseScoreOf(TEAM_ONE, TEAM_TWO, TEAM_ONE);

        gameManager.undoGoal();

        dataExtractor.setGameManager(gameManager);
        int actual = dataExtractor.extractScoreOf(TEAM_ONE);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        gameManager.initGame(initDataModel);

        gameManager.undoGoal();

        dataExtractor.setGameManager(gameManager);
        int actualScoreTeamOne = dataExtractor.extractScoreOf(TEAM_ONE);
        int actualScoreTeamTwo = dataExtractor.extractScoreOf(TEAM_TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_decrease_the_number_of_won_sets_when_win_condition_has_been_fulfilled() {
        gameManager.initGame(initDataModel);
        super.raiseScoreOf(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE);

        gameManager.undoGoal();

        dataExtractor.setGameManager(gameManager);
        int actual = dataExtractor.extractNumberOfWonSetsOf(TEAM_ONE);
        assertThat(actual).isEqualTo(0);
    }
}
