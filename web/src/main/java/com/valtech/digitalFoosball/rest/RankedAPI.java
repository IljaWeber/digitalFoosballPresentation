package com.valtech.digitalFoosball.rest;

import com.valtech.digitalFoosball.domain.IPlayDigitalFoosball;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("ranked")
public class RankedAPI extends BaseAPI {

    @Autowired
    public RankedAPI(@Qualifier("facade_bean") IPlayDigitalFoosball facade) {
        super(facade);
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());
        SessionIdentifier identifier = initDataModel.getIdentifier();
        UUID explicitIdentifier = identifier.getIdentifier();
        initDataModel.setMode(GameMode.RANKED);

        iPlayAGame.initGame(initDataModel, explicitIdentifier);

        return iPlayAGame.getGameData(explicitIdentifier);
    }
}
