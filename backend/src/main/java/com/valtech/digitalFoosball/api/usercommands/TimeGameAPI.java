package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.api.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.timeGame.TimeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("timegame_controller")
@RequestMapping("time")
public class TimeGameAPI extends BaseAPI {

    private final RaspiController raspiController;

    @Autowired
    public TimeGameAPI(TimeGame game, RaspiController raspiController) {
        super(game);
        this.raspiController = raspiController;
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init() {
        logger.info("New Time-Game");

        raspiController.setGame(IPlayAGame);

        IPlayAGame.initGame(new InitDataModel());

        return IPlayAGame.getGameData();
    }
}
