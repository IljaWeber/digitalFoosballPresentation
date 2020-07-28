package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.common.GameController;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;

@RestController()
@RequestMapping("api")
public class
DigitalFoosballUserCommandAPI {

    private final Logger
            logger;
    private final GameController
            gameController;

    @Autowired
    public DigitalFoosballUserCommandAPI(GameController gameController) {
        this.gameController = gameController;
        logger = LogManager.getLogger(DigitalFoosballUserCommandAPI.class);
    }

    @PostMapping(path = "/init/adhoc", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init() {
        logger.info("New AdHoc-Game");

        InitDataModel initDataModel = new InitDataModel();
        initDataModel.setMode(AD_HOC);
        gameController.initGame(initDataModel);

        return gameController.getGameData();
    }

    @PostMapping(path = "/init/ranked", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());

        initDataModel.setMode(RANKED);
        gameController.initGame(initDataModel);

        return gameController.getGameData();
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
