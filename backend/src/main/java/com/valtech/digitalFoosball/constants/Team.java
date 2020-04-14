package com.valtech.digitalFoosball.constants;

public enum Team {
    NO_TEAM(0),
    ONE(1),
    TWO(2);

    private int teamNumber;

    Team(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public static Team getTeamBy(int teamNo) {
        if (ONE.teamNumber == teamNo) {
            return ONE;
        }

        return TWO;
    }

    public int getInt() {
        return teamNumber;
    }
}
