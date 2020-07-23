package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.ScoreOverView;

import java.util.SortedMap;

public interface GameDataModel {
    void countGoalFor(Team team);

    SortedMap<Team, RankedTeamDataModel> getTeams();

    void setWinnerOfAGame(Team team);

    void undoLastGoal();

    boolean thereAreGoals();

    boolean areThereUndoneGoals();

    void redoLastUndoneGoal();

    void changeOver();

    void resetMatch();

    Team getWinner();

    ScoreOverView getOverView();
}
