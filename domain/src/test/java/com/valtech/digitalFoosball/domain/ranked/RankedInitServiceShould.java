package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedInitServiceShould {

    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;

    private RankedInitService initService;

    @BeforeEach
    void setUp() {
        initService = new RankedInitService(new TeamServiceFake());
    }

    @Test
    void init_game_with_the_names_from_the_initDataModel() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        GameDataModel gameDataModel = initService.init(initDataModel);
        TeamDataModel teamOne = gameDataModel.getTeam(ONE);
        TeamDataModel teamTwo = gameDataModel.getTeam(TWO);

        assertThat(teamOne)
                .extracting(TeamDataModel::getName,
                            TeamDataModel::getNameOfPlayerOne,
                            TeamDataModel::getNameOfPlayerTwo)
                .contains("T1", "P1", "P2");
        assertThat(teamTwo)
                .extracting(TeamDataModel::getName,
                            TeamDataModel::getNameOfPlayerOne,
                            TeamDataModel::getNameOfPlayerTwo)
                .contains("T2", "P3", "P4");
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
