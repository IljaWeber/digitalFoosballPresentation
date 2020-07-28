package com.valtech.digitalFoosball.domain.timeGame;

public enum GameState {
    FIRST_HALF,
    HALFTIME,
    SECOND_HALF,
    OVER;

    public GameState getNext() {
        switch (this) {
            case FIRST_HALF:
                return HALFTIME;
            case HALFTIME:
                return SECOND_HALF;
            case SECOND_HALF:
                return OVER;
        }

        return FIRST_HALF;
    }

    public boolean isActive() {
        switch (this) {
            case FIRST_HALF:
            case SECOND_HALF:
                return true;
            default:
                return false;
        }
    }
}
