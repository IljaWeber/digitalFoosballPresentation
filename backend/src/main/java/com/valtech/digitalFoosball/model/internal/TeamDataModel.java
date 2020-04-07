package com.valtech.digitalFoosball.model.internal;

import com.valtech.digitalFoosball.service.TeamModels;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity(name = "team")
public class TeamDataModel implements TeamModels {
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<PlayerDataModel> players;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Transient
    private int score;

    @Transient
    private int wonRounds;

    private int wonMatches;

    public TeamDataModel() {
        players = Arrays.asList(new PlayerDataModel(), new PlayerDataModel());
        name = "";
        score = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerDataModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDataModel> players) {
        this.players = players;
    }

    public String getNameOfPlayerOne() {
        return players.get(0).getName();
    }

    public String getNameOfPlayerTwo() {
        return players.get(1).getName();
    }

    public void setNameOfPlayerOne(String name) {
        players.get(0).setName(name);
    }

    public void setNameOfPlayerTwo(String name) {
        players.get(1).setName(name);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void countGoal() {
        score++;
    }

    public void decreaseScore() {
        score--;
    }

    public void resetScore() {
        score = 0;
    }

    public int getWonRounds() {
        return wonRounds;
    }

    public void increaseWonRounds() {
        wonRounds++;
    }

    public void decreaseWonRounds() {
        wonRounds--;
    }

    public void resetValues() {
        name = "";
        score = 0;
        for (PlayerDataModel player : players) {
            player.resetValues();
        }
    }

    @Override
    public String toString() {
        return name + ": " + getNameOfPlayerOne() + ", " + getNameOfPlayerTwo();
    }

    public void increaseWonMatches() {
        wonMatches++;
    }

    public int getWonMatches() {
        return wonMatches;
    }
}
