package com.valtech.digitalFoosball.domain.common.models;

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