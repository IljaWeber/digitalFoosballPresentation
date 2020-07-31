package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.api.driver.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adhoc_controller")
@RequestMapping("adhoc")
public class AdHocAPI extends BaseAPI {

    private final RaspiController raspiController;

    @Autowired
    public AdHocAPI(AdHocGame game,
                    RaspiController raspiController) {
        super(game);
        this.raspiController = raspiController;
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init() {
        logger.info("New AdHoc-Game");

        raspiController.setGame(IPlayAGame);

        IPlayAGame.initGame(new InitDataModel());

        return IPlayAGame.getGameData();
    }
}