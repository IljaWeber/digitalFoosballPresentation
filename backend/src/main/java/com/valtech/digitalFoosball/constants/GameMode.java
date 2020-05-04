package com.valtech.digitalFoosball.constants;

public enum GameMode {
    AD_HOC(0),
    RANKED(1),
    TIME_GAME(2),
    NO_ACTIVE_GAME(3);

    private final int gameMode;

    GameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public static GameMode getModeBy(int gameModeId) {
        switch (gameModeId) {
            case 0:
                return AD_HOC;
            case 1:
                return RANKED;
            case 2:
                return TIME_GAME;
            default:
                return NO_ACTIVE_GAME;
        }
    }
}
