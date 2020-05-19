package com.valtech.digitalFoosball.domain.gameModes.manipulators;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.converter.Converter;
import com.valtech.digitalFoosball.domain.gameModes.models.AbstractGameModelFactory;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;

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
        List<TeamDataModel> teamsFromDatabase = new ArrayList<>();

        List<TeamDataModel> teamsList = initDataModel.getTeams();

        for (TeamDataModel team : teamsList) {
            TeamDataModel teamFromDatabase = teamDataPort.loadOrSaveIntoDatabase(team);
            teamsFromDatabase.add(teamFromDatabase);
        }

        gameDataModel = AbstractGameModelFactory.createGameDataModel(mode);

        gameDataModel.setTeams(teamsFromDatabase);

        return gameDataModel;
    }

    public List<TeamOutput> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamDataPort.getAllTeamsFromDatabase();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }
}
