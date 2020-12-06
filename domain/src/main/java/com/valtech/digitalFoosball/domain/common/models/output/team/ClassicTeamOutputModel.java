package com.valtech.digitalFoosball.domain.common.models.output.team;

public class ClassicTeamOutputModel implements TeamOutputModel {
    private String name;
    private String playerOne;
    private String playerTwo;
    private int score;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPlayerOne() {
        return playerOne;
    }

    @Override
    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    @Override
    public String getPlayerTwo() {
        return playerTwo;
    }

    @Override
    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

}
