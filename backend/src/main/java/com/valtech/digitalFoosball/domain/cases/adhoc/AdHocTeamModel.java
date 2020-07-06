package com.valtech.digitalFoosball.domain.cases.adhoc;

public interface AdHocTeamModel {
    String getName();

    void setName(String name);

    int getScore();

    void countGoal();

    void decreaseScore();

    void changeover();

    @Override
    String toString();
}
