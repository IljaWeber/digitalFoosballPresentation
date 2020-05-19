package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.GameController;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.BaseOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutputModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.domain.constants.GameMode.RANKED;

@RestController()
@RequestMapping("api")
public class DigitalFoosballRestAPI {

    private final Logger logger = LogManager.getLogger(DigitalFoosballRestAPI.class);
    private final GameController gameController;

    @Autowired
    public DigitalFoosballRestAPI(GameController gameController) {
        this.gameController = gameController;
    }

    @PostMapping(path = "/init/adhoc", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseOutputModel init() {
        logger.info("New AdHoc-Game");

        InitDataModel initDataModel = new InitDataModel();
        initDataModel.setMode(AD_HOC);
        gameController.initGame(initDataModel);

        return gameController.getGameData();
    }

    @PostMapping(path = "/init/ranked", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseOutputModel init(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());

        initDataModel.setMode(RANKED);
        gameController.initGame(initDataModel);

        return gameController.getGameData();
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseOutputModel getGameData() {
        return gameController.getGameData();
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutputModel> getAllTeams() {
        return gameController.getAllTeams();
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody int teamNo) {
        logger.info("Score raised for {}", teamNo);

        Team team = Team.getTeamBy(teamNo);

        gameController.countGoalFor(team);
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseOutputModel newRound() {
        logger.info("New Round");

        gameController.changeover();

        return gameController.getGameData();
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseOutputModel undoLastGoal() {
        logger.info("Undo");

        gameController.undoGoal();

        return gameController.getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseOutputModel redoLastGoal() {
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
