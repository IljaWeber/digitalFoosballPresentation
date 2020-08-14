package com.valtech.digitalFoosball.domain.timeGame.service;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class ScoreConverter {

    public static MatchScores convert(Stack<Team> goalOverView) {
        MatchScores scores = new MatchScores();

        int scoreOfTeamOne = getScoreOfTeam(ONE, goalOverView);
        scores.setScoreOfTeamOne(scoreOfTeamOne);

        int scoreOfTeamTwo = getScoreOfTeam(TWO, goalOverView);
        scores.setScoreOfTeamTwo(scoreOfTeamTwo);

        return scores;
    }

    private static int getScoreOfTeam(Team one, Stack<Team> goalOverView) {
        return Collections.frequency(goalOverView, one);
    }
}
