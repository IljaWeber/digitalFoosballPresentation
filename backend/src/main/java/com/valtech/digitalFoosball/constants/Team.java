package com.valtech.digitalFoosball.constants;

public enum Team {
    ONE(1),
    TWO(2),
    NO_TEAM(3);

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

    public int value() {
        return teamNumber;
    }
}
