package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DigitalFoosballGameRulesRulesShouldUndoLastGoal {

    public IPlayAGame rankedGame;

    @BeforeEach
    void setUp() {
        TeamDataModel teamOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamOne, teamTwo);
        rankedGame = new DigitalFoosballGameRules(new RankedInitService(new TeamServiceFake()));

        rankedGame.initGame(initDataModel);
    }

    @Test
    void in_the_reversed_order_of_scoring() {
        raiseScoreOf(ONE, TWO, ONE);

        rankedGame.undoGoal();

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        GameOutputModel gameData = rankedGame.getGameData();
        return gameData.getTeam(team).getScore();
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        rankedGame.undoGoal();

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_decrease_the_number_of_won_sets_when_win_condition_has_been_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);

        rankedGame.undoGoal();

        ClassicGameOutputModel gameData = (ClassicGameOutputModel) rankedGame.getGameData();
        Team actual = gameData.getWinnerOfSet();
        assertThat(actual).isEqualTo(NO_TEAM);
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            rankedGame.countGoalFor(team);
        }
    }

    private class TeamServiceFake implements RankedGamePersistencePort {

        @Override
        public TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel) {

            return teamDataModel;
        }

        @Override
        public List<TeamDataModel> getAllTeamsFromDatabase() {
            return null;
        }
    }
}
