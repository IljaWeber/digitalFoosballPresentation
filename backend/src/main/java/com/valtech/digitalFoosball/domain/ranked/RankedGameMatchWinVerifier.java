package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.MatchWinVerifier;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class RankedGameMatchWinVerifier implements MatchWinVerifier {

    @Override
    public Team getMatchWinner(Stack<Team> allWins) {
        List<Team> wins = new ArrayList<>(allWins);
        Team matchWinner = NO_TEAM;
        int winsOfTeamOne = 0;
        int winsOfTeamTwo = 0;
        int necessarySetWinsForMatchWin = 2;

        for (Team win : wins) {

            if (win == ONE) {
                winsOfTeamOne++;

                if (winsOfTeamOne >= necessarySetWinsForMatchWin) {
                    matchWinner = ONE;
                }
            }

            if (win == TWO) {
                winsOfTeamTwo++;

                if (winsOfTeamTwo >= necessarySetWinsForMatchWin) {
                    matchWinner = TWO;
                }
            }
        }

        return matchWinner;
    }
}
