package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;

    public GameDataModel() {
        teams = new TreeMap<>();
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public void resetMatch() {
        teams.clear();
    }
}