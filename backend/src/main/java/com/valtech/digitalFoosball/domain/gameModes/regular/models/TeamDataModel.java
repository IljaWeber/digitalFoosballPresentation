package com.valtech.digitalFoosball.domain.gameModes.regular.models;

import com.valtech.digitalFoosball.domain.constants.Team;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.constants.Team.NO_TEAM;

@Entity(name = "team")
public class TeamDataModel {
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
    private int wonSets;

    private int wonMatches;

    private Team team;

    public TeamDataModel() {
        players = Arrays.asList(new PlayerDataModel(), new PlayerDataModel());
        name = "";
        score = 0;
        team = NO_TEAM;
    }

    public TeamDataModel(String teamName, String playerOne, String playerTwo) {
        players = Arrays.asList(new PlayerDataModel(), new PlayerDataModel());
        score = 0;
        name = teamName;
        team = NO_TEAM;
        setNameOfPlayerOne(playerOne);
        setNameOfPlayerTwo(playerTwo);
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

    public void setNameOfPlayerOne(String name) {
        players.get(0).setName(name);
    }

    public String getNameOfPlayerTwo() {
        return players.get(1).getName();
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

    public void changeover() {
        score = 0;
    }

    public int getWonSets() {
        return wonSets;
    }

    public void increaseWonSets() {
        wonSets++;
    }

    public void decreaseWonSets() {
        wonSets--;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isTeam(Team team) {
        return this.team == team;
    }
}
