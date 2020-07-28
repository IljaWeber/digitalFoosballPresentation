package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassicGameInitService implements InitService {
    protected final IObtainTeams teamDataPort;

    public ClassicGameInitService(IObtainTeams teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    protected GameDataModel prepare(InitDataModel initDataModel) {
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

    public List<TeamOutputModel> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamDataPort.getAllTeamsFromDatabase();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }
}
