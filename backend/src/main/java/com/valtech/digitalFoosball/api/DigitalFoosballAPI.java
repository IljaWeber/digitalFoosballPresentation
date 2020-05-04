package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.GameController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class DigitalFoosballAPI {

    private final Logger logger = LogManager.getLogger(DigitalFoosballAPI.class);
    private final GameController gameController;

    @Autowired
    public DigitalFoosballAPI(GameController gameController) {
        this.gameController = gameController;
    }

    @PostMapping(path = "/initialize/{gameModeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel initialize(@RequestBody InitDataModel initDataModel, @PathVariable int gameModeId) {
        logger.info("Sign in: " + initDataModel.toString() + ", for Game Mode: " + gameModeId);
        GameMode mode = GameMode.getModeBy(gameModeId);

        gameController.initGame(initDataModel, mode);

        return gameController.getGameData();
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel getGameData() {
        return gameController.getGameData();
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutput> getAllTeamsStartingWith() {
        return gameController.getAllTeamsFromDatabase();
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody int teamNo) {
        logger.info("Score raised for {}", teamNo);

        Team team = Team.getTeamBy(teamNo);

        gameController.countGoalFor(team);
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel newRound() {
        logger.info("New Round");

        gameController.changeover();

        return gameController.getGameData();
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel undoLastGoal() {
        logger.info("Undo");

        gameController.undoGoal();

        return gameController.getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel redoLastGoal() {
        logger.info("Redo");

        gameController.redoGoal();

        return gameController.getGameData();
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues() {
        logger.info("Reset");

        gameController.resetMatch();

        return true;
    }
}
