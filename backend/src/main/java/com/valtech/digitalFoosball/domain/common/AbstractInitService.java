package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.AbstractGameModelFactory;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInitService {

    private final IObtainTeams teamDataPort;

    public AbstractInitService(IObtainTeams teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    public abstract GameDataModel init(InitDataModel initDataModel);

    protected GameDataModel prepare(InitDataModel initDataModel) {
        GameDataModel gameDataModel;
        GameMode mode = initDataModel.getMode();
        List<RankedTeamDataModel> teamsFromDatabase = new ArrayList<>();

        List<RankedTeamDataModel> teamsList = initDataModel.getTeams();

        for (RankedTeamDataModel team : teamsList) {
            RankedTeamDataModel teamFromDatabase = teamDataPort.loadOrSaveIntoDatabase(team);
            teamsFromDatabase.add(teamFromDatabase);
        }

        gameDataModel = AbstractGameModelFactory.createGameDataModel(mode);

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
