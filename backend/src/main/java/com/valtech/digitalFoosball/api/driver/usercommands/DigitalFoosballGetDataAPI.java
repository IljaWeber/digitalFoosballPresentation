package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.common.GameController;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class
DigitalFoosballGetDataAPI {

    private final GameController gameController;

    @Autowired
    public DigitalFoosballGetDataAPI(GameController gameController) {
        this.gameController = gameController;
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutputModel> getAllTeams() {
        return gameController.getAllTeams();
    }

    @GetMapping(path = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel getGameData() {
        return gameController.getGameData();
    }

}
