package com.valtech.digitalFoosball.api.sensorcommands;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.usercommands.DigitalFoosballFacade;
import com.valtech.digitalFoosball.domain.RaiseScoreIdentifier;
import com.valtech.digitalFoosball.domain.SessionIdentifier;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("raspi_controller")
@RequestMapping("raspi")
public class RaspiController {

    private final DigitalFoosballFacade facade;
    private final INotifyAboutStateChanges publisher;
    private final Logger logger;

    @Autowired
    public RaspiController(INotifyAboutStateChanges publisher, DigitalFoosballFacade facade) {
        this.publisher = publisher;
        logger = LogManager.getLogger(RaspiController.class);
        this.facade = facade;
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody RaiseScoreIdentifier raiseScoreIdentifier) {
        int teamNo = raiseScoreIdentifier.getTeamNo();
        SessionIdentifier identifier = raiseScoreIdentifier.getIdentifier();
        logger.info("Score raised for {}", teamNo);
        Team team = Team.getTeamBy(teamNo);

        facade.countGoalFor(team, identifier.getIdentifier());

        publisher.notifyAboutStateChange(facade.getGameData(identifier.getIdentifier()));
    }

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public SessionIdentifier register() {
        logger.info("Raspberry Pi registered");

        return facade.registerAvailableRaspBerry();
    }
}
