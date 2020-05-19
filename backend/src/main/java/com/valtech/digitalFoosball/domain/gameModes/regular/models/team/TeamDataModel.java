package com.valtech.digitalFoosball.domain.gameModes.regular.models.team;

public interface TeamDataModel {
    String getName();

    void setName(String name);

    int getScore();

    void countGoal();

    void decreaseScore();

    void changeover();

    @Override
    String toString();
}
