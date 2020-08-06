package com.valtech.digitalFoosball.api.sensorcommands;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("raspi_controller")
@RequestMapping("raspi")
public class RaspiController {

    private IPlayAGame iPlayAGame;
    private final INotifyAboutStateChanges publisher;

    private final Logger logger;

    public RaspiController(INotifyAboutStateChanges publisher) {
        this.publisher = publisher;
        logger = LogManager.getLogger(RaspiController.class);
    }

    @PostMapping(path = "/raise", produces = MediaType.APPLICATION_JSON_VALUE)
    public void raiseScore(@RequestBody int teamNo) {
        logger.info("Score raised for {}", teamNo);

        Team team = Team.getTeamBy(teamNo);

        iPlayAGame.countGoalFor(team);
        publisher.notifyAboutStateChange(iPlayAGame.getGameData());
    }

    public void setGame(IPlayAGame IPlayAGame) {
        this.iPlayAGame = IPlayAGame;
    }
}