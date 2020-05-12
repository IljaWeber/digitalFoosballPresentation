package com.valtech.digitalFoosball.api.driver.sensorcommands;

import com.valtech.digitalFoosball.domain.constants.Team;

public interface IReactToGoals {
    void countGoalFor(Team team);
}
