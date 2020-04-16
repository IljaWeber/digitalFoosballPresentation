package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.Converter;
import com.valtech.digitalFoosball.service.verifier.UniqueNameVerifier;
import com.valtech.digitalFoosball.storage.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {

    private TeamService teamService;

    @Autowired
    public TeamManager(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<TeamDataModel> init(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        return loadTeamsFromDatabase(initDataModel);
    }

    private List<TeamDataModel> loadTeamsFromDatabase(InitDataModel initDataModel) {
        List<TeamDataModel> teamsFromDatabase = new ArrayList<>();

        List<TeamDataModel> teamsList = initDataModel.getTeams();

        for (TeamDataModel team : teamsList) {
            TeamDataModel teamFromDatabase = teamService.setUp(team);
            teamsFromDatabase.add(teamFromDatabase);
        }

        return teamsFromDatabase;
    }

    public List<TeamDataModel> initAdHocGame() {
        TeamDataModel teamDataModelOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamDataModelTwo = new TeamDataModel("Green", "Goalie", "Striker");

        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        return loadTeamsFromDatabase(initDataModel);
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        List<TeamDataModel> teamDataModels = teamService.getAll();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }

}
