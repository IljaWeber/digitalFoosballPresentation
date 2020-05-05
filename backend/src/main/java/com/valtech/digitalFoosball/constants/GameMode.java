package com.valtech.digitalFoosball.constants;

public enum GameMode {
    AD_HOC(),
    RANKED(),
    TIME_GAME(),
    NO_ACTIVE_GAME();

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
