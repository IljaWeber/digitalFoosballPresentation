package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class ScoreConverter {

    public static MatchScores convert(Stack<Team> goalOverView) {
        MatchScores scores = new MatchScores();

        int scoreOfTeamOne = Collections.frequency(goalOverView, ONE);
        scores.setScoreOfTeamOne(scoreOfTeamOne);

        int scoreOfTeamTwo = Collections.frequency(goalOverView, TWO);
        scores.setScoreOfTeamTwo(scoreOfTeamTwo);

        return scores;
    }
}
