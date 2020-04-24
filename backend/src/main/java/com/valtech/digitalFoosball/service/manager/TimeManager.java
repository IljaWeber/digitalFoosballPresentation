package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.SortedMap;

public class TimeManager {
    private SortedMap<Team, TeamDataModel> teams;

    public void setTeams(SortedMap<Team, TeamDataModel> teams) {
        this.teams = teams;
    }

    public void countGoalFor(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        teamDataModel.countGoal();
    }

}
