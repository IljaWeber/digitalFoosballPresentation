package com.valtech.digitalFoosball.domain.common.models.output.team;

public interface TeamOutputModel {
    String getName();

    void setName(String name);

    String getPlayerOne();

    void setPlayerOne(String playerOne);

    String getPlayerTwo();

    void setPlayerTwo(String playerTwo);

    int getScore();

    void setScore(int score);
}
