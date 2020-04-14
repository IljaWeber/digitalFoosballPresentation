package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.factories.InitDataModelBuilder;
import com.valtech.digitalFoosball.factories.TeamDataModelBuilder;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.verifier.MatchWinVerifier;
import com.valtech.digitalFoosball.service.verifier.SetWinVerifier;
import com.valtech.digitalFoosball.service.verifier.UniqueNameVerifier;
import com.valtech.digitalFoosball.storage.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameManager {
    private MatchWinVerifier matchWinVerifier = new MatchWinVerifier();
    private SortedMap<Team, TeamDataModel> teams;
    private TeamService teamService;
    private Stack<Team> historyOfGoals;
    private Stack<Team> historyOfUndo;
    private SetWinVerifier setWinVerifier;
    private Team setWinner;

    @Autowired
    public GameManager(TeamService teamService) {
        this.teamService = teamService;
        historyOfGoals = new Stack<>();
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
        historyOfGoals = new Stack<>();
        historyOfUndo = new Stack<>();
    }

    public void countGoalFor(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        if (setWinner == Team.NO_TEAM) {
            teamDataModel.countGoal();
            historyOfGoals.push(team);

            if (setWinVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public void undoGoal() {
        if (!historyOfGoals.empty()) {
            Team team = historyOfGoals.pop();
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
            historyOfGoals.push(team);

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

        List<TeamOutput> teamOutputs = Converter.convertMapToTeamOutputs(teams);

        GameDataModel currentGameData = new GameDataModel(teamOutputs);

        int setWinnerInt = setWinner.getInt();
        currentGameData.setWinnerOfSet(setWinnerInt);

        int matchWinner = matchWinVerifier.getMatchWinner(teams);
        currentGameData.setMatchWinner(matchWinner);

        return currentGameData;
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
        return historyOfGoals;
    }

    public Stack<Team> getHistoryOfUndo() {
        return historyOfUndo;
    }
}
