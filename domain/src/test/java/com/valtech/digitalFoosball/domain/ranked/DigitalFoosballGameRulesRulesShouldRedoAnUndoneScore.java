package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.usecases.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class DigitalFoosballGameRulesRulesShouldRedoAnUndoneScore {

    public IPlayAGame game;

    @BeforeEach
    void setUp() {
        TeamDataModel teamOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamOne, teamTwo);
        game = new DigitalFoosballGameRules(new RankedInitService(new TeamServiceFake()));

        game.initGame(initDataModel);
    }

    @Test
    void if_a_score_has_been_undone_recently() {
        raiseScoreOf(ONE);
        game.undoGoal();

        game.redoGoal();

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        GameOutputModel gameData = game.getGameData();
        return gameData.getTeam(team).getScore();
    }

    @Test
    void only_when_a_goal_was_undid_otherwise_do_nothing() {
        game.redoGoal();

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_raise_the_won_sets_if_necessary() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        game.undoGoal();

        game.redoGoal();

        Team actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(ONE);
    }

    private Team getNumberOfWonSets(Team team) {
        ClassicGameOutputModel gameData = (ClassicGameOutputModel) game.getGameData();
        return gameData.getWinnerOfSet();
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team);
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
