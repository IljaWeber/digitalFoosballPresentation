package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.InitService;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankedInitService implements InitService {

    private final IObtainTeams teamDataPort;

    @Autowired
    public RankedInitService(IObtainTeams teamDataPort) {
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