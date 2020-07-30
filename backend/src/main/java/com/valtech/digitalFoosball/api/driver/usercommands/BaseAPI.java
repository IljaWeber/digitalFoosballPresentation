package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public abstract class BaseAPI {
    protected final Logger logger;
    protected final IPlayAGame game;

    public BaseAPI(IPlayAGame game) {
        logger = LogManager.getLogger(AdHocAPI.class);
        this.game = game;
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel getGameData() {
        return game.getGameData();
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody int teamNo) {
        logger.info("Score raised for {}", teamNo);

        Team team = Team.getTeamBy(teamNo);

        game.countGoalFor(team);
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel newRound() {
        logger.info("New Round");

        game.changeover();

        return getGameData();
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel undoLastGoal() {
        logger.info("Undo");

        game.undoGoal();

        return getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel redoLastGoal() {
        logger.info("Redo");

        game.redoGoal();

        return getGameData();
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues() {
        logger.info("Reset");

        game.resetMatch();

        return true;
    }
}