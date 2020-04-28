package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.manager.IReactToGoals;
import com.valtech.digitalFoosball.service.manager.IReactToPlayerCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class DigitalFoosballAPI {

    private final IReactToGoals goalPort;
    private final IReactToPlayerCommands playerCommandPort;
    private final Logger logger = LogManager.getLogger(DigitalFoosballAPI.class);

    @Autowired
    public DigitalFoosballAPI(IReactToGoals goalPort, IReactToPlayerCommands playerCommandPort) {
        this.goalPort = goalPort;
        this.playerCommandPort = playerCommandPort;
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel initGame(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());

        playerCommandPort.initGame(initDataModel);

        return playerCommandPort.getGameData();
    }

    @PostMapping(path = "/initAdHoc", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel initAdHocGame() {
        logger.info("Ad-Hoc-Game started");

        playerCommandPort.initAdHocGame();

        return playerCommandPort.getGameData();
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel getGameData() {
        return playerCommandPort.getGameData();
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutput> getAllTeamsStartingWith() {
        return playerCommandPort.getAllTeamsFromDatabase();
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody int teamNo) {
        logger.info("Score raised for {}", teamNo);

        Team team = Team.getTeamBy(teamNo);

        goalPort.countGoalFor(team);
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel newRound() {
        logger.info("New Round");

        playerCommandPort.changeover();

        return playerCommandPort.getGameData();
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel undoLastGoal() {
        logger.info("Undo");

        playerCommandPort.undoGoal();

        return playerCommandPort.getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel redoLastGoal() {
        logger.info("Redo");

        playerCommandPort.redoGoal();

        return playerCommandPort.getGameData();
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues() {
        logger.info("Reset");

        playerCommandPort.resetMatch();

        return true;
    }
}
