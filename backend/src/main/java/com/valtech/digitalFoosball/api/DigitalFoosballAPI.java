package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.Game;
import com.valtech.digitalFoosball.service.game.factory.GameFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class DigitalFoosballAPI {

    private Game game;

    private final Logger logger = LogManager.getLogger(DigitalFoosballAPI.class);

    @Autowired
    private GameFactory gameFactory;

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel initGame(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());

        game = gameFactory.getGame(GameMode.RANKED);

        game.initGame(initDataModel);

        return game.getGameData();
    }

    @PostMapping(path = "/initAdHoc", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel initAdHocGame() {
        logger.info("Ad-Hoc-Game started");

        game = gameFactory.getGame(GameMode.AD_HOC);

        game.initGame(new InitDataModel());

        return game.getGameData();
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel getGameData() {
        return game.getGameData();
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutput> getAllTeamsStartingWith() {
        return game.getAllTeamsFromDatabase();
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

        return game.getGameData();
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel undoLastGoal() {
        logger.info("Undo");

        game.undoGoal();

        return game.getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel redoLastGoal() {
        logger.info("Redo");

        game.redoGoal();

        return game.getGameData();
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues() {
        logger.info("Reset");

        game.resetMatch();

        return true;
    }
}
