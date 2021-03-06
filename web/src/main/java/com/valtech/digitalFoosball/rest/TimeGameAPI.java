package com.valtech.digitalFoosball.rest;

import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.IPlayDigitalFoosball;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("time")
public class TimeGameAPI extends BaseAPI {

    @Autowired
    public TimeGameAPI(IPlayDigitalFoosball facade) {
        super(facade);
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody UUID assosiatedRaspBerryId) {
        logger.info("New Time-Game");
        InitDataModel initDataModel = new InitDataModel();
        initDataModel.setMode(GameMode.TIME_GAME);

        iPlayAGame.initGame(initDataModel, assosiatedRaspBerryId);

        return iPlayAGame.getGameData(assosiatedRaspBerryId);
    }
}
