package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
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


@Controller
@RestController
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
        gameManager.raiseScore(teamNo);
        logger.info("Score raise was called for {}", teamNo);

        updateClient();
    }

    @PostMapping(path = "/newRound", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel newRound() {
        gameManager.newRound();

        return gameManager.getGameData();
    }

    private void updateClient() {
        template.convertAndSend("/update/score", gameManager.getGameData());
    }

    @PutMapping(path = "/undo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel undoLastGoal() {
        gameManager.undoLastGoal();

        return gameManager.getGameData();
    }

    @PutMapping(path = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDataModel redoLastGoal() {
        gameManager.redoLastGoal();

        return gameManager.getGameData();
    }

    @DeleteMapping(path = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean resetGameValues() {
        gameManager.resetGameValues();

        return true;
    }
}
