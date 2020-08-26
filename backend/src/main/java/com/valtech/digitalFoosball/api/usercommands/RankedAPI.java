package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.domain.SessionIdentifier;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;

@RestController("ranked_controller")
@RequestMapping("ranked")
public class RankedAPI extends BaseAPI {

    @Autowired
    public RankedAPI(DigitalFoosballFacade facade) {
        super(facade);
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody InitDataModel initDataModel) {
        logger.info("Sign in: " + initDataModel.toString());
        SessionIdentifier identifier = initDataModel.getIdentifier();
        UUID explicitIdentifier = identifier.getIdentifier();
        initDataModel.setMode(RANKED);

        iPlayAGame.initGame(initDataModel, explicitIdentifier);

        return iPlayAGame.getGameData(explicitIdentifier);
    }
}
