package com.valtech.digitalFoosball.domain.common.constants;

public enum GameMode {
    RANKED, AD_HOC;

    @Override
    public String toString() {
        if (this == GameMode.AD_HOC) {
            return "/adhoc";
        }
        return "/ranked";
    }
}
