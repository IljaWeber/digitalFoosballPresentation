package com.valtech.digitalFoosball.rest;

import com.valtech.digitalFoosball.domain.IPlayDigitalFoosball;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("adhoc")
public class AdHocAPI extends BaseAPI {

    public AdHocAPI(IPlayDigitalFoosball facade) {
        super(facade);
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody SessionIdentifier assosiatedRaspBerryId) {
        logger.info("New AdHoc-Game");
        InitDataModel initDataModel = new InitDataModel();
        initDataModel.setMode(GameMode.AD_HOC);

        iPlayAGame.initGame(initDataModel, assosiatedRaspBerryId.getIdentifier());

        return iPlayAGame.getGameData(assosiatedRaspBerryId.getIdentifier());
    }

}
