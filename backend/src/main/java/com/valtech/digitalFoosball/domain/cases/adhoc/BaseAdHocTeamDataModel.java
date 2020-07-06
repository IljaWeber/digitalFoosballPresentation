package com.valtech.digitalFoosball.domain.cases.adhoc;

import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseAdHocTeamDataModel implements AdHocTeamModel {
    protected String name;

    @ManyToMany(cascade = CascadeType.ALL)
    protected List<PlayerDataModel> players;

    @Transient
    protected int score;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    public BaseAdHocTeamDataModel() {
        players = Arrays.asList(new PlayerDataModel(), new PlayerDataModel());
        score = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
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

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void countGoal() {
        score++;
    }

    @Override
    public void decreaseScore() {
        score--;
    }

    @Override
    public void changeover() {
        score = 0;
    }

    @Override
    public String toString() {
        return name + ": " + getNameOfPlayerOne() + ", " + getNameOfPlayerTwo();
    }
}
