package com.valtech.digitalFoosball.service;

public interface TeamModels {
    void countGoal();

    void decreaseScore();

    void increaseWonRounds();

    void decreaseWonRounds();

    int getWonRounds();

    int getScore();
}
