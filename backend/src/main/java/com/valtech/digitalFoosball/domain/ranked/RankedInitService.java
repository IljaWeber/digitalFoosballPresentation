package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankedInitService {

    private final IObtainTeams teamDataPort;

    @Autowired
    public RankedInitService(IObtainTeams teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    public RankedGameDataModel init(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        return prepare(initDataModel);
    }

    private RankedGameDataModel prepare(InitDataModel initDataModel) {
        RankedGameDataModel gameDataModel = new RankedGameDataModel();
        List<RankedTeamDataModel> teamsFromDatabase = new ArrayList<>();

        List<RankedTeamDataModel> teamsList = initDataModel.getTeams();

        for (RankedTeamDataModel team : teamsList) {
            RankedTeamDataModel teamFromDatabase = teamDataPort.loadOrSaveIntoDatabase(team);
            teamsFromDatabase.add(teamFromDatabase);
        }
        gameDataModel.setTeams(teamsFromDatabase);

        return gameDataModel;
    }

    public List<TeamOutputModel> getAllTeams() {
        List<RankedTeamDataModel> teamDataModels = teamDataPort.getAllTeamsFromDatabase();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }
}
