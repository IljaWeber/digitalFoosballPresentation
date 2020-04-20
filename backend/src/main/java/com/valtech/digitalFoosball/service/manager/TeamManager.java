package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.converter.Converter;
import com.valtech.digitalFoosball.service.verifier.UniqueNameVerifier;
import com.valtech.digitalFoosball.storage.IObtainTeams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamManager {

    private final IObtainTeams teamDataPort;

    @Autowired
    public TeamManager(IObtainTeams teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    public List<TeamDataModel> init(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        return prepare(initDataModel);
    }

    private List<TeamDataModel> prepare(InitDataModel initDataModel) {
        List<TeamDataModel> teamsFromDatabase = new ArrayList<>();

        List<TeamDataModel> teamsList = initDataModel.getTeams();

        for (TeamDataModel team : teamsList) {
            TeamDataModel teamFromDatabase = teamDataPort.loadOrSaveIntoDatabase(team);
            teamsFromDatabase.add(teamFromDatabase);
        }

        return teamsFromDatabase;
    }

    public List<TeamDataModel> initAdHocGame() {
        TeamDataModel teamDataModelOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamDataModelTwo = new TeamDataModel("Green", "Goalie", "Striker");

        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        return prepare(initDataModel);
    }

    public List<TeamOutput> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamDataPort.getAllTeamsFromDatabase();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }
}