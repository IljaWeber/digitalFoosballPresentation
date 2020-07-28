package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class ClassicScoreManager {

    // todo: created on 28.07.20 by iljaweber: refactor this
    protected Team checkForWin(Stack<Team> goalOverView) {
        Team winner = NO_TEAM;
        int neededGoals = 6;
        int scoreOfTeamOne = Collections.frequency(goalOverView, ONE);
        int scoreOfTeamTwo = Collections.frequency(goalOverView, TWO);

        if (scoreOfTeamOne >= neededGoals) {
            if (scoreOfTeamOne - scoreOfTeamTwo >= 2) {
                winner = ONE;
            }
        }

        if (scoreOfTeamTwo >= neededGoals) {
            if (scoreOfTeamTwo - scoreOfTeamOne >= 2) {
                winner = TWO;
            }
        }
        return winner;
    }
}
