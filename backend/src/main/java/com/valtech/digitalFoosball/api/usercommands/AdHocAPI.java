package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.AD_HOC;

@RestController("adhoc_controller")
@RequestMapping("adhoc")
public class AdHocAPI extends BaseAPI {

    @Autowired
    public AdHocAPI(DigitalFoosballFacade facade) {
        super(facade);
    }

    @PostMapping(path = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public GameOutputModel init(@RequestBody SessionIdentifier assosiatedRaspBerryId) {
        logger.info("New AdHoc-Game");
        InitDataModel initDataModel = new InitDataModel();
        initDataModel.setMode(AD_HOC);

        iPlayAGame.initGame(initDataModel, assosiatedRaspBerryId.getIdentifier());

        return iPlayAGame.getGameData(assosiatedRaspBerryId.getIdentifier());
    }

}
