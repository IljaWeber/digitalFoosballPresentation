package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Stack;

public interface MatchWinVerifier {
    Team getMatchWinner(Stack<Team> allWins);
}
