package com.valtech.digitalFoosball.domain.common.constants;

public enum GameMode {
    RANKED, AD_HOC;

    @Override
    public String toString() {
        switch (this) {
            case AD_HOC:
                return "/adhoc";
            default:
                return "/ranked";
        }
    }
}
