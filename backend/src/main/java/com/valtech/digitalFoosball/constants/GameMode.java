package com.valtech.digitalFoosball.constants;

public enum GameMode {
    AD_HOC(0),
    RANKED(1),
    TIME_GAME(2),
    NO_ACTIVE_GAME;

    private int gameMode;

    GameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public static GameMode getModeBy(int gameModeId) {
        if (gameModeId == AD_HOC.gameMode) {
            return AD_HOC;
        }

        if (gameModeId == RANKED.gameMode) {
            return RANKED;
        }

        return TIME_GAME;
    }
}
