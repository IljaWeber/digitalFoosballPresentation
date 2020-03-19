package com.valtech.digitalFoosball.service;

public interface TeamModels {
    void increaseScore();

    void decreaseScore();

    void increaseWonRounds();

    void decreaseWonRounds();

    int getWonRounds();

    int getScore();
}
