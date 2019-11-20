package com.valtech.digitalFoosball.model.internal;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "team")
public class TeamDataModel {
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<PlayerDataModel> players;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Transient
    private int score;

    @Transient
    private int wonRounds;

    public int getScore() {
        return score;
    }

    public int getWonRounds() {
        return wonRounds;
    }

    public TeamDataModel() {
        players = new ArrayList<>();
        name = "";
        score = 0;

        players.add(new PlayerDataModel());
        players.add(new PlayerDataModel());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameOfPlayerOne(String name) {
        players.get(0).setName(name);
    }

    public void setNameOfPlayerTwo(String name) {
        players.get(1).setName(name);
    }

    public String getNameOfPlayerOne() {
        return players.get(0).getName();
    }

    public String getNameOfPlayerTwo() {
        return players.get(1).getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<PlayerDataModel> getPlayers() {
        return players;
    }

    public void increaseScore() {
        score++;
    }

    public void decreaseScore() {
        score--;
    }

    public void increaseWonRounds() {
        wonRounds++;
    }

    public void resetValues() {
        name = "";
        score = 0;
        for (PlayerDataModel player : players) {
            player.resetValues();
        }
    }

    public void resetScore() {
        score = 0;
    }

    public void decreaseWonRounds() {
        wonRounds--;
    }

    public void setPlayers(List<PlayerDataModel> players) {
        this.players = players;
    }
}
