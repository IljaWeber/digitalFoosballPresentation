package com.valtech.digitalFoosball.domain.usecases.ranked.service;

import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;

import java.util.ArrayList;
import java.util.List;

public class RankedInitService implements IInitializeGames {

    private final RankedGamePersistencePort teamDataPort;

    public RankedInitService(RankedGamePersistencePort teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    public GameDataModel init(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        return prepare(initDataModel);
    }

    private GameDataModel prepare(InitDataModel initDataModel) {
        GameDataModel gameDataModel = new GameDataModel();
        List<TeamDataModel> teamsFromDatabase = new ArrayList<>();

        List<TeamDataModel> teamsList = initDataModel.getTeams();

        for (TeamDataModel team : teamsList) {
            TeamDataModel teamFromDatabase = teamDataPort.loadOrSaveIntoDatabase(team);
            teamsFromDatabase.add(teamFromDatabase);
        }

        gameDataModel.setTeams(teamsFromDatabase);

        return gameDataModel;
    }
}
