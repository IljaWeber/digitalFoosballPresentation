package com.valtech.digitalFoosball.domain.common.models;

public class PlayerDataModel {

    private String name;

    public PlayerDataModel() {
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
