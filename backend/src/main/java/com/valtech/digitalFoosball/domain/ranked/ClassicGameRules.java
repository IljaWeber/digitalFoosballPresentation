package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class ClassicGameRules implements IKnowTheRules {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private final Stack<Team> winOverview;
    private Team currentSetWinner = NO_TEAM;

    public ClassicGameRules() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        winOverview = new Stack<>();
    }

    public Team getCurrentSetWinner() {
        return currentSetWinner;
    }

    // OPTIMIZE: created on 28.07.20 by iljaweber: reconsider implementation of raiseScoreFor

    public void raiseScoreFor(Team team) {

        if (currentSetWinner != NO_TEAM) {
            return;
        }

        goalOverView.push(team);

        checkForWin();

        if (currentSetWinner != NO_TEAM) {
            winOverview.push(currentSetWinner);
        }
    }

    public int getScoreOfTeam(Team team) {
        if (goalOverView.isEmpty()) {
            return 0;
        }

        return Collections.frequency(goalOverView, team);
    }

    public Team getMatchWinner() {
        for (Team team : Team.getTeams()) {
            if (Collections.frequency(winOverview, team) >= 2) {
                return team;
            }
        }

        return NO_TEAM;
    }

    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        if (currentSetWinner != NO_TEAM) {
            winOverview.pop();
            currentSetWinner = NO_TEAM;
        }

        Team team = goalOverView.pop();
        undoOverView.push(team);
    }

    public void redoLastGoal() {
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    public void changeOver() {
        goalOverView.clear();
        undoOverView.clear();
        currentSetWinner = NO_TEAM;
    }

    private void checkForWin() {
        int neededGoals = 6;
        int scoreOfTeamOne = Collections.frequency(goalOverView, ONE);
        int scoreOfTeamTwo = Collections.frequency(goalOverView, TWO);

        if (scoreOfTeamOne >= neededGoals) {
            if (scoreOfTeamOne - scoreOfTeamTwo >= 2) {
                currentSetWinner = ONE;
            }
        }

        if (scoreOfTeamTwo >= neededGoals) {
            if (scoreOfTeamTwo - scoreOfTeamOne >= 2) {
                currentSetWinner = TWO;
            }
        }
    }
}