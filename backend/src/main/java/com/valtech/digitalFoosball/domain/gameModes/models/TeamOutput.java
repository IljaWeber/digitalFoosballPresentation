package com.valtech.digitalFoosball.domain.gameModes.models;

public class TeamOutput {
    private String name;
    private String playerOne;
    private String playerTwo;
    private int score;
    private int setWins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSetWins() {
        return setWins;
    }

    public void setWinsForActualSet(int setWins) {
        this.setWins = setWins;
    }
}

