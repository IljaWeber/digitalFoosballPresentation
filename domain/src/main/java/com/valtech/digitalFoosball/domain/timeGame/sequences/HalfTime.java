package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;
import com.valtech.digitalFoosball.domain.timeGame.service.MatchScores;
import com.valtech.digitalFoosball.domain.timeGame.service.ScoreConverter;

import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class HalfTime implements IPlayATimeGame {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private final TimeGameRules rules;

    public HalfTime(Stack<Team> goalOverView,
                    Stack<Team> undoOverView,
                    TimeGameRules rules) {
        this.goalOverView = goalOverView;
        this.undoOverView = undoOverView;
        this.rules = rules;
    }

    @Override
    public void raiseScoreFor(Team team) {

    }

    @Override
    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        undoOverView.push(goalOverView.pop());
    }

    @Override
    public void redoLastGoal() {
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    @Override
    public void changeover() {
        IPlayATimeGame secondHalf = new SecondHalf(goalOverView, undoOverView, rules);
        rules.setActualTimeGameSequence(secondHalf);
    }

    @Override
    public MatchScores getMatchScores() {
        return ScoreConverter.convert(goalOverView);
    }

    @Override
    public String toString() {
        return "Half Time";
    }

    @Override
    public Team getMatchWinner() {
        return NO_TEAM;
    }

    @Override
    public void timeRanDown() {
    }
}
