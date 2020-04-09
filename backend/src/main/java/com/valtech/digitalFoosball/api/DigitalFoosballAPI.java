package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.GameManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RestController()
@RequestMapping("api")
public class DigitalFoosballAPI {

    private GameManager gameManager;
    private SimpMessagingTemplate template;
    private Logger logger = LogManager.getLogger(DigitalFoosballAPI.class);

    @Autowired
    public DigitalFoosballAPI(GameManager gameManager, SimpMessagingTemplate template) {
        this.gameManager = gameManager;
        this.template = template;
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel initGame(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());

        gameManager.initGame(initDataModel);

        return gameManager.getGameData();
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel getGameData() {
        return gameManager.getGameData();
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutput> getAllTeamsStartingWith() {
        return gameManager.getAllTeams();
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody int teamNo) {
        logger.info("Score raised for {}", teamNo);

        Team team = Team.getTeamBy(teamNo);

        gameManager.countGoalFor(team);

        updateClient();
    }

    private void updateClient() {
        template.convertAndSend("/update/score", gameManager.getGameData());
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel newRound() {
        logger.info("New Round");

        gameManager.changeover();

        return gameManager.getGameData();
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel undoLastGoal() {
        logger.info("Undo");

        gameManager.undoGoal();

        return gameManager.getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel redoLastGoal() {
        logger.info("Redo");

        gameManager.redoGoal();

        return gameManager.getGameData();
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues() {
        logger.info("Reset");

        gameManager.resetMatch();

        return true;
    }

    @PostMapping(path = "/initAdHoc", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel initAdHocGame() {
        logger.info("Ad-Hoc-Game started");

        gameManager.initAdHocGame();

        return gameManager.getGameData();
    }
}
