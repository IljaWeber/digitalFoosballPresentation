package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.api.IUpdateClient;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.storage.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static com.valtech.digitalFoosball.constants.Team.TWO;

@Service
public class GameManager {
    private final IUpdateClient clientUpdater;
    private final TeamManager teamManager;
    private final ScoreManager scoreManager;
    private final SortedMap<Team, TeamDataModel> teams;

    @Autowired
    public GameManager(TeamService teamService, IUpdateClient clientUpdater) {
        teamManager = new TeamManager(teamService);
        scoreManager = new ScoreManager();
        teams = new TreeMap<>();
        this.clientUpdater = clientUpdater;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        return teamManager.getAllTeamsFromDatabase();
    }

    public void initGame(InitDataModel initDataModel) {
        List<TeamDataModel> teamDataModels = teamManager.init(initDataModel);

        setUpTeams(teamDataModels);
    }

    public void initAdHocGame() {
        List<TeamDataModel> teamDataModels = teamManager.initAdHocGame();

        setUpTeams(teamDataModels);
    }

    private void setUpTeams(List<TeamDataModel> teamDataModels) {
        setUpTeam(ONE, teamDataModels);
        setUpTeam(TWO, teamDataModels);

        scoreManager.setTeams(teams);
    }

    private void setUpTeam(Team team, List<TeamDataModel> teamDataModels) {
        TeamDataModel teamOne = teamDataModels.get(team.listAssociationNumber());
        teamOne.setTeam(team);
        teams.put(team, teamOne);
    }

    public void countGoalFor(Team team) {
        scoreManager.countGoalFor(team);
        clientUpdater.updateClientWith(getGameData());
    }

    public void undoGoal() {
        scoreManager.undoGoal();
    }

    public void redoGoal() {
        scoreManager.redoGoal();
    }

    public void changeover() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());

        scoreManager.resetOldGameValues();
    }

    public void resetMatch() {
        teams.forEach((teamConstant, dataModel) -> dataModel.resetValues());

        scoreManager.resetOldGameValues();
    }

    public GameDataModel getGameData() {
        if (teams.isEmpty()) {
            return new GameDataModel();
        }

        Team setWinner = scoreManager.getSetWinner();

        return new GameDataModel(teams, setWinner);
    }
}