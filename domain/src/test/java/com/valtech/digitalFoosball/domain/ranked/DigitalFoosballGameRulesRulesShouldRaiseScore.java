package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class DigitalFoosballGameRulesRulesShouldRaiseScore {

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
    void in_the_order_of_scoring() {
        raiseScoreOf(ONE, ONE, ONE, TWO);

        int scoreOfTeamOne = getScoreOfTeam(ONE);
        int scoreOfTeamTwo = getScoreOfTeam(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(3);
        assertThat(scoreOfTeamTwo).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        GameOutputModel gameData = game.getGameData();

        return gameData.getTeam(team).getScore();
    }

    @Test
    void only_until_the_win_condition_is_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(6);
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
