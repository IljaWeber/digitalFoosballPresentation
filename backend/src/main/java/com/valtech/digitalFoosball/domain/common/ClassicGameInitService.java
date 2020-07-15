package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassicGameInitService {
    protected final IObtainTeams teamDataPort;

    public ClassicGameInitService(IObtainTeams teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    protected RankedGameDataModel prepare(InitDataModel initDataModel) {
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
