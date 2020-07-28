package com.valtech.digitalFoosball.domain.common.constants;

import java.util.Arrays;
import java.util.List;

public enum Team {
    ONE(1, 0),
    TWO(2, 1),
    NO_TEAM(0, 999);

    private final int hardwareAssociationNumber;
    private final int listAssociationNumber;

    Team(int hardwareAssociationNumber, int listAssociationNumber) {
        this.hardwareAssociationNumber = hardwareAssociationNumber;
        this.listAssociationNumber = listAssociationNumber;
    }

    public static Team getTeamBy(int teamNo) {
        if (ONE.hardwareAssociationNumber == teamNo) {
            return ONE;
        }

        return TWO;
    }

    public int hardwareValue() {
        return hardwareAssociationNumber;
    }

    public int listAssociationNumber() {
        return listAssociationNumber;
    }

    public Team getOpponent() {
        if (hardwareAssociationNumber == 1) {
            return TWO;
        } else {
            return ONE;
        }
    }

    public static List<Team> getTeams() {
        return Arrays.asList(ONE, TWO);
    }
}
