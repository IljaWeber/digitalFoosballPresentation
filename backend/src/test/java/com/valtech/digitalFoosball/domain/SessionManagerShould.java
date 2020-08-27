package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.api.IObtainTeams;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGameRules;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionManager;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerShould {

    @Test
    public void register_a_client_to_its_desired_raspberry_pi_and_notify_their_related_identifier() {
        SessionManager manager = new SessionManager();
        UUID availableRaspberry = manager.registerRaspberryPiWithId();
        IPlayAGame gameRules = createAdHocGame();

        UUID relatedIdentifier = manager.setSession(availableRaspberry, gameRules);

        IPlayAGame gameData = manager.getSession(relatedIdentifier);
        List<TeamOutputModel> actual = gameData.getGameData().getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
    }

    @Test
    public void register_multiple_clients_to_their_desired_raspberry_pi_and_notify_their_related_identifier() {
        SessionManager manager = new SessionManager();
        UUID availableRaspberryOne = manager.registerRaspberryPiWithId();
        UUID availableRaspberryTwo = manager.registerRaspberryPiWithId();
        IPlayAGame gameRulesOne = createAdHocGame();
        IPlayAGame gameRulesTwo = createRankedGame();

        UUID relatedIdentifierOfMatchOne = manager.setSession(availableRaspberryOne, gameRulesOne);
        UUID relatedIdentifierOfMatchTwo = manager.setSession(availableRaspberryTwo, gameRulesTwo);

        IPlayAGame gameData = manager.getSession(relatedIdentifierOfMatchOne);
        List<TeamOutputModel> actualGameOne = gameData.getGameData().getTeams();
        assertThat(actualGameOne).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");

        gameData = manager.getSession(relatedIdentifierOfMatchTwo);
        List<TeamOutputModel> actualGameTwo = gameData.getGameData().getTeams();
        assertThat(actualGameTwo).extracting(TeamOutputModel::getName).containsExactly("FC Barcelona", "FC Madrid");
    }

    @Test
    public void show_all_available_raspberry_pi() {
        SessionManager manager = new SessionManager();
        manager.registerRaspberryPiWith("Office Munich #1");
        manager.registerRaspberryPiWith("Office Cologne #1");

        List<String> actual = manager.getAllAvailableRaspberryPi();

        assertThat(actual).contains("Office Munich #1", "Office Cologne #1");
    }

    @Test
    public void register_clients_to_their_desired_playgrounds() {
        SessionManager manager = new SessionManager();
        manager.registerRaspberryPiWith("Office Munich #1");
        String clientsPlayground = "Office Munich #1";
        IPlayAGame game = createAdHocGame();

        manager.registerClientToPlayground(clientsPlayground, game);

        IPlayAGame gameData = manager.getSession(clientsPlayground);
        List<TeamOutputModel> actual = gameData.getGameData().getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
    }

    private IPlayAGame createAdHocGame() {
        AdHocInitService initService = new AdHocInitService();
        AdHocGameRules gameRules = new AdHocGameRules(initService);
        gameRules.initGame(new InitDataModel());
        return gameRules;
    }

    private IPlayAGame createRankedGame() {
        RankedInitServiceFake initService = new RankedInitServiceFake(null);
        IPlayAGame rules = new RankedGameRules(initService);
        InitDataModel initDataModel = new InitDataModel();
        List<TeamDataModel> teams = new ArrayList<>();
        TeamDataModel teamOne = new TeamDataModel("FC Barcelona",
                                                  "Marc-Andre ter Stegen", "Lionel Messi");
        TeamDataModel teamTwo = new TeamDataModel("FC Madrid",
                                                  "Thibaut Courtois", "Gareth Bale");
        teams.add(teamOne);
        teams.add(teamTwo);
        initDataModel.setTeams(teams);

        rules.initGame(initDataModel);

        return rules;
    }

    private class RankedInitServiceFake extends RankedInitService {

        public RankedInitServiceFake(IObtainTeams teamDataPort) {
            super(teamDataPort);
        }

        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            GameDataModel gameDataModel = new GameDataModel();

            gameDataModel.setTeams(initDataModel.getTeams());

            return gameDataModel;
        }
    }
}
