package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGameRules;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import com.valtech.digitalFoosball.domain.common.session.SessionManager;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import com.valtech.digitalFoosball.domain.timeGame.TimeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DigitalFoosballFacade {
    private final INotifyAboutStateChanges publisher;
    private final SessionManager sessionManager;
    private RankedInitService rankedInitService;

    @Autowired
    public DigitalFoosballFacade(RankedInitService rankedInitService,
                                 INotifyAboutStateChanges publisher) {
        this.publisher = publisher;
        this.sessionManager = new SessionManager();
        this.rankedInitService = rankedInitService;
    }

    public void initGame(InitDataModel initDataModel, UUID raspberryId) {
        IPlayAGame rules = initializeGameRules(initDataModel);

        sessionManager.setSession(raspberryId, rules);
    }

    private IPlayAGame initializeGameRules(InitDataModel initDataModel) {
        IPlayAGame rules = null;

        if (initDataModel.getMode() == GameMode.RANKED) {
            rules = new RankedGameRules(rankedInitService);
            rules.initGame(initDataModel);
        }

        if (initDataModel.getMode() == GameMode.AD_HOC) {
            rules = new AdHocGameRules(new AdHocInitService());
            rules.initGame(initDataModel);
        }

        if (initDataModel.getMode() == GameMode.TIME_GAME) {
            rules = new TimeGame(new AdHocInitService(), publisher);
            rules.initGame(initDataModel);
        }

        return rules;
    }

    public void countGoalFor(Team team, UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).countGoalFor(team);
    }

    public void changeover(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).changeover();

    }

    public void undoGoal(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).undoGoal();
    }

    public void redoGoal(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).redoGoal();
    }

    public void resetMatch(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).resetMatch();
    }

    public GameOutputModel getGameData(UUID relatedIdentifier) {
        IPlayAGame session = sessionManager.getSession(relatedIdentifier);
        return session.getGameData();
    }

    public SessionIdentifier registerAvailableRaspBerry() {
        SessionIdentifier identifier = new SessionIdentifier();

        identifier.setId(sessionManager.registerRaspberryPiWithId());

        return identifier;
    }
}
