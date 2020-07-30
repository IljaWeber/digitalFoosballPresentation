package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.api.driver.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ranked_controller")
@RequestMapping("ranked")
public class RankedAPI extends BaseAPI {

    private final RaspiController raspiController;

    @Autowired
    public RankedAPI(RankedGame game,
                     RaspiController raspiController) {
        super(game);
        this.raspiController = raspiController;
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());

        raspiController.setGame(game);

        game.initGame(initDataModel);

        return game.getGameData();
    }
}