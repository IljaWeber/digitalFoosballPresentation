package com.valtech.digitalFoosball.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity(name = "team")
public class TeamDataEntity {

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<PlayerDataEntity> players;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    public TeamDataEntity() {
        players = Arrays.asList(new PlayerDataEntity(), new PlayerDataEntity());
        name = "";
    }

    public TeamDataEntity(String teamName, String playerOne, String playerTwo) {
        this();
        name = teamName;
        setNameOfPlayerOne(playerOne);
        setNameOfPlayerTwo(playerTwo);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerDataEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDataEntity> players) {
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

    public String toString() {
        return name + ": " + getNameOfPlayerOne() + ", " + getNameOfPlayerTwo();
    }
}
