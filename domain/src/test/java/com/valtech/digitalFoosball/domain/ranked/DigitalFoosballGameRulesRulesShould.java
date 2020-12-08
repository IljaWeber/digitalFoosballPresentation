package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.usecases.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DigitalFoosballGameRulesRulesShould {
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
    public void throw_name_duplicate_exception_when_a_name_is_used_twice() {
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P1");

        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> game.initGame(initDataModel));
    }

    private List<TeamOutputModel> getTeamDataModels() {
        GameOutputModel gameData = game.getGameData();

        return gameData.getTeams();
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        raiseScoreOf(ONE, TWO);

        game.changeover();

        List<TeamOutputModel> teams = getTeamDataModels();
        assertThat(teams).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamOutputModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamOutputModel::getPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamOutputModel::getPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void forget_about_shot_goals_from_the_past_set() {
        raiseScoreOf(ONE);
        game.undoGoal();
        game.changeover();

        game.undoGoal();

        GameOutputModel gameData = game.getGameData();
        int actual = gameData.getTeam(ONE).getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void forget_about_undid_goals_from_the_past_set() {
        raiseScoreOf(ONE);
        game.undoGoal();
        game.changeover();

        game.redoGoal();

        GameOutputModel gameData = game.getGameData();
        int actual = gameData.getTeam(ONE).getScore();
        assertThat(actual).isEqualTo(0);
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



