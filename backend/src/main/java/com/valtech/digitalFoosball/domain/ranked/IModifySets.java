package com.valtech.digitalFoosball.domain.ranked;

public interface IModifySets {
    void increaseWonSets();

    void decreaseWonSets();

    int getWonSets();
}
