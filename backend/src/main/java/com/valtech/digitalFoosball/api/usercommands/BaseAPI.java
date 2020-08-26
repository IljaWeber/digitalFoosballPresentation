package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.domain.RaiseScoreIdentifier;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public abstract class BaseAPI {
    protected final Logger logger;
    protected final DigitalFoosballFacade iPlayAGame;

    public BaseAPI(DigitalFoosballFacade facade) {
        this.iPlayAGame = facade;
        logger = LogManager.getLogger(AdHocAPI.class);
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel getGameData(@RequestBody SessionIdentifier relatedIdentifier) {
        return iPlayAGame.getGameData(relatedIdentifier.getIdentifier());
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody RaiseScoreIdentifier identifier) {
        logger.info("Score raised for {}", identifier.getTeamNo());
        Team team = Team.getTeamBy(identifier.getTeamNo());
        UUID relatedIdentifier = identifier.getIdentifier().getIdentifier();

        iPlayAGame.countGoalFor(team, relatedIdentifier);
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel newRound(@RequestBody SessionIdentifier relatedIdentifier) {
        logger.info("New Round");

        iPlayAGame.changeover(relatedIdentifier.getIdentifier());

        return getGameData(relatedIdentifier);
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel undoLastGoal(@RequestBody SessionIdentifier relatedIdentifier) {
        logger.info("Undo");

        iPlayAGame.undoGoal(relatedIdentifier.getIdentifier());

        return getGameData(relatedIdentifier);
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel redoLastGoal(@RequestBody SessionIdentifier relatedIdentifier) {
        logger.info("Redo");

        iPlayAGame.redoGoal(relatedIdentifier.getIdentifier());

        return getGameData(relatedIdentifier);
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues(@RequestBody SessionIdentifier relatedIdentifier) {
        logger.info("Reset");

        iPlayAGame.resetMatch(relatedIdentifier.getIdentifier());

        return true;
    }
}
