package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;

public interface IKnowTheRules {
    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoLastGoal();

    void changeOver();
}
