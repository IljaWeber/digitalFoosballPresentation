package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.usecases.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.usecases.ranked.service.RankedInitService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class DigitalFoosballFacadeShould {

    private final DigitalFoosballFacade facade;

    public DigitalFoosballFacadeShould() {
        facade = createDigitalFoosballFacade();
    }

    @Test
    public void register_a_client_with_its_data_to_an_available_raspberry_pi() {
        SessionIdentifier id = facade.registerAvailableRaspBerry();
        UUID assignedId = id.getIdentifier();
        InitDataModel adHoc = createAdHocInitDataModel();

        facade.initGame(adHoc, assignedId);

        GameOutputModel gameData = facade.getGameData(assignedId);
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
    }

    @Test
    public void raise_score() {
        SessionIdentifier id = facade.registerAvailableRaspBerry();
        UUID assignedId = id.getIdentifier();
        InitDataModel adHocGame = createAdHocInitDataModel();
        facade.initGame(adHocGame, assignedId);

        raiseScoreFor(assignedId,
                      ONE, ONE, ONE,
                      TWO, TWO,
                      ONE);

        GameOutputModel gameData = facade.getGameData(assignedId);
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(4, 2);
    }

    @Test
    public void undo_last_goal() {
        SessionIdentifier id = facade.registerAvailableRaspBerry();
        UUID assignedId = id.getIdentifier();
        InitDataModel adHocGame = createAdHocInitDataModel();
        facade.initGame(adHocGame, assignedId);
        raiseScoreFor(assignedId,
                      ONE);

        facade.undoGoal(assignedId);

        GameOutputModel gameData = facade.getGameData(assignedId);
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
    }

    @Test
    public void redo_last_undone_goal() {
        SessionIdentifier id = facade.registerAvailableRaspBerry();
        UUID assignedId = id.getIdentifier();
        InitDataModel adHocGame = createAdHocInitDataModel();
        facade.initGame(adHocGame, assignedId);
        raiseScoreFor(assignedId,
                      ONE);
        facade.undoGoal(assignedId);

        facade.redoGoal(assignedId);

        GameOutputModel gameData = facade.getGameData(assignedId);
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(1, 0);
    }

    @Test
    public void changeover_a_won_set() {
        SessionIdentifier id = facade.registerAvailableRaspBerry();
        UUID assignedId = id.getIdentifier();
        InitDataModel adHocGame = createAdHocInitDataModel();
        facade.initGame(adHocGame, assignedId);
        raiseScoreFor(assignedId,
                      ONE, ONE, ONE,
                      TWO, TWO,
                      ONE, ONE, ONE);

        facade.changeover(assignedId);

        GameOutputModel gameData = facade.getGameData(assignedId);
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
    }

    @Test
    public void reset_all_goal_to_zero_and_empty_all_names() {
        SessionIdentifier id = facade.registerAvailableRaspBerry();
        UUID assignedId = id.getIdentifier();
        InitDataModel adHocGame = createAdHocInitDataModel();
        facade.initGame(adHocGame, assignedId);
        raiseScoreFor(assignedId,
                      ONE,
                      TWO, TWO);

        facade.resetMatch(assignedId);

        GameOutputModel gameData = facade.getGameData(assignedId);
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).isEmpty();
    }

    private void raiseScoreFor(UUID assignedId, Team... teams) {
        for (Team team : teams) {
            facade.countGoalFor(team, assignedId);
        }
    }

    private DigitalFoosballFacade createDigitalFoosballFacade() {
        return new DigitalFoosballFacade(new RankedInitServiceFake(null), new AdHocInitService(), new FakePublisher());
    }

    private InitDataModel createAdHocInitDataModel() {
        InitDataModel adHoc = new InitDataModel();
        adHoc.setMode(AD_HOC);
        return adHoc;
    }

    private class RankedInitServiceFake extends RankedInitService {

        public RankedInitServiceFake(RankedGamePersistencePort teamDataPort) {
            super(teamDataPort);
        }

        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            GameDataModel gameDataModel = new GameDataModel();

            gameDataModel.setTeams(initDataModel.getTeams());

            return gameDataModel;
        }
    }

    private class FakePublisher implements INotifyAboutStateChanges {

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
        }
    }
}
