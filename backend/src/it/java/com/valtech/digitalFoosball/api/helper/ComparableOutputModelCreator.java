package com.valtech.digitalFoosball.api.helper;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.team.ClassicTeamOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class ComparableOutputModelCreator {
    private final List<TeamOutputModel> teams;
    private Team matchWinner;
    private Team winnerOfSet;
    private TeamOutputModel teamTwo;
    private TeamOutputModel teamOne;

    public ComparableOutputModelCreator() {
        teams = new ArrayList<>();
        matchWinner = NO_TEAM;
        winnerOfSet = NO_TEAM;
    }

    public void prepareCompareTeamOneWithValues(String name, String nameOfPlayerOne, String nameOfPlayerTwo) {
        teamOne = new ClassicTeamOutputModel();
        teamOne.setName(name);
        teamOne.setPlayerOne(nameOfPlayerOne);
        teamOne.setPlayerTwo(nameOfPlayerTwo);
        teams.add(teamOne);
    }

    public void prepareCompareTeamTwoWithValues(String name, String nameOfPlayerOne, String nameOfPlayerTwo) {
        teamTwo = new ClassicTeamOutputModel();
        teamTwo.setName(name);
        teamTwo.setPlayerOne(nameOfPlayerOne);
        teamTwo.setPlayerTwo(nameOfPlayerTwo);
        teams.add(teamTwo);
    }

    public void prepareScoreOfTeamOne(int score) {
        teamOne.setScore(score);
    }

    public void prepareScoreOfTeamTwo(int score) {
        teamTwo.setScore(score);
    }

    public void setMatchWinner(Team matchWinner) {
        this.matchWinner = matchWinner;
    }

    public void setWinnerOfSet(Team winnerOfSet) {
        this.winnerOfSet = winnerOfSet;
    }

    public List<TeamOutputModel> getTeams() {
        return teams;
    }

    public Team getMatchWinner() {
        return matchWinner;
    }

    public Team getWinnerOfSet() {
        return winnerOfSet;
    }
}
