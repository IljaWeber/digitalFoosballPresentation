package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.GoalHistory;
import com.valtech.digitalFoosball.service.histories.UndoHistory;
import com.valtech.digitalFoosball.service.verifier.SetWinVerifier;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class ScoreManager {
    private SortedMap<Team, TeamDataModel> teams;

    private GoalHistory goalHistory;
    private UndoHistory undoHistory;
    private final SetWinVerifier setWinVerifier;
    private Team setWinner;

    public ScoreManager() {
        goalHistory = new GoalHistory();
        setWinVerifier = new SetWinVerifier();
        teams = new TreeMap<>();
        undoHistory = new UndoHistory();
    }

    public void setTeams(SortedMap<Team, TeamDataModel> teams) {
        this.teams = teams;
        resetOldGameValues();
    }

    void resetOldGameValues() {
        setWinner = Team.NO_TEAM;
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();
    }

    public void countGoalFor(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        if (setHasNoWinner()) {
            teamDataModel.countGoal();
            goalHistory.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    private boolean setHasNoWinner() {
        return setWinner == Team.NO_TEAM;
    }

    public void undoGoal() {
        if (goalHistory.thereAreGoals()) {
            Team team = goalHistory.removeOneGoalFromHistory();
            TeamDataModel lastScoringTeam = teams.get(team);

            if (setWinner != Team.NO_TEAM) {
                lastScoringTeam.decreaseWonSets();
                setWinner = Team.NO_TEAM;
            }

            lastScoringTeam.decreaseScore();

            undoHistory.rememberUndoneGoal(team);
        }
    }

    public void redoGoal() {
        if (undoHistory.hasUndoneGoals()) {
            Team team = undoHistory.removeUndoneGoal();
            TeamDataModel teamDataModel = teams.get(team);

            teamDataModel.countGoal();
            goalHistory.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public Team getSetWinner() {
        return setWinner;
    }
}
