package com.valtech.digitalFoosball.domain.usecases.timeGame.service;

public class MatchScores {
    private int scoreOfTeamOne;
    private int scoreOfTeamTwo;

    public int getScoreOfTeamOne() {
        return scoreOfTeamOne;
    }

    public void setScoreOfTeamOne(int scoreOfTeamOne) {
        this.scoreOfTeamOne = scoreOfTeamOne;
    }

    public int getScoreOfTeamTwo() {
        return scoreOfTeamTwo;
    }

    public void setScoreOfTeamTwo(int scoreOfTeamTwo) {
        this.scoreOfTeamTwo = scoreOfTeamTwo;
    }
}
