package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.builders.GameDataModelBuilder;
import com.valtech.digitalFoosball.builders.InitDataModelBuilder;
import com.valtech.digitalFoosball.builders.TeamDataModelBuilder;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.verifier.SetWinVerifier;
import com.valtech.digitalFoosball.service.verifier.UniqueNameVerifier;
import com.valtech.digitalFoosball.storage.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameManager {
    private SortedMap<Team, TeamDataModel> teams;
    private TeamService teamService;
    private GoalHistory historyOfGoals;
    private Stack<Team> historyOfUndo;
    private SetWinVerifier setWinVerifier;
    private Team setWinner;

    @Autowired
    public GameManager(TeamService teamService) {
        this.teamService = teamService;
        historyOfGoals = new GoalHistory();
        historyOfUndo = new Stack<>();
        setWinVerifier = new SetWinVerifier();
        teams = new TreeMap<>();
    }

    public void initGame(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        prepareAndStartMatch(initDataModel);
    }

    public void initAdHocGame() {
        TeamDataModel teamDataModelOne = TeamDataModelBuilder.buildWithAdHocPlayerNames("Orange");
        TeamDataModel teamDataModelTwo = TeamDataModelBuilder.buildWithAdHocPlayerNames("Green");

        InitDataModel initDataModel = InitDataModelBuilder.buildWithTeams(teamDataModelOne, teamDataModelTwo);

        prepareAndStartMatch(initDataModel);
    }

    private void prepareAndStartMatch(InitDataModel initDataModel) {
        resetGameValues();

        List<TeamDataModel> teamsList = initDataModel.getTeams();
        teams.put(Team.ONE, teamsList.get(0));
        teams.put(Team.TWO, teamsList.get(1));

        for (TeamDataModel team : teamsList) {
            teamService.setUp(team);
        }
    }

    private void resetGameValues() {
        setWinner = Team.NO_TEAM;
        historyOfGoals = new GoalHistory();
        historyOfUndo = new Stack<>();
    }

    public void countGoalFor(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        if (setWinner == Team.NO_TEAM) {
            teamDataModel.countGoal();
            historyOfGoals.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public void undoGoal() {
        if (historyOfGoals.thereAreGoals()) {
            Team team = historyOfGoals.removeOneGoalFromHistory();
            TeamDataModel lastScoringTeam = teams.get(team);

            if (setWinner != Team.NO_TEAM) {
                lastScoringTeam.decreaseWonSets();
                setWinner = Team.NO_TEAM;
            }

            lastScoringTeam.decreaseScore();
            historyOfUndo.push(team);
        }
    }

    public void redoGoal() {
        if (!historyOfUndo.empty()) {
            Team team = historyOfUndo.pop();
            TeamDataModel teamDataModel = teams.get(team);

            teamDataModel.countGoal();
            historyOfGoals.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public void resetMatch() {
        teams.forEach((teamConstant, dataModel) -> dataModel.resetValues());

        resetGameValues();
    }

    public void changeover() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());

        resetGameValues();
    }

    public GameDataModel getGameData() {
        if (teams.isEmpty()) {
            return null;
        }

        int setWinnerInt = setWinner.value();

        return GameDataModelBuilder.buildWithTeamsAndSetWinner(teams, setWinnerInt);
    }

    public Map<Team, TeamDataModel> getCurrentTeams() {
        return teams;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        List<TeamDataModel> teamDataModels = teamService.getAll();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }

    public Stack<Team> getHistoryOfGoals() {
        return historyOfGoals.getHistoryOfGoals();
    }

    public Stack<Team> getHistoryOfUndo() {
        return historyOfUndo;
    }
}
