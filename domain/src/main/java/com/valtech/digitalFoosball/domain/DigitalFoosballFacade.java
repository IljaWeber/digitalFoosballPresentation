package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.adhoc.AdHocGameRules;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import com.valtech.digitalFoosball.domain.common.session.SessionManager;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.timeGame.TimeGame;

import java.util.UUID;

public class DigitalFoosballFacade implements IPlayDigitalFoosball {
    private final INotifyAboutStateChanges publisher;
    private final SessionManager sessionManager;
    private IInitializeGames rankedInitService;

    public DigitalFoosballFacade(IInitializeGames rankedInitService,
                                 INotifyAboutStateChanges publisher) {
        this.publisher = publisher;
        this.sessionManager = new SessionManager();
        this.rankedInitService = rankedInitService;
    }

    @Override
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

    @Override
    public void countGoalFor(Team team, UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).countGoalFor(team);
    }

    @Override
    public void changeover(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).changeover();

    }

    @Override
    public void undoGoal(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).undoGoal();
    }

    @Override
    public void redoGoal(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).redoGoal();
    }

    @Override
    public void resetMatch(UUID relatedIdentifier) {
        sessionManager.getSession(relatedIdentifier).resetMatch();
    }

    @Override
    public GameOutputModel getGameData(UUID relatedIdentifier) {
        IPlayAGame session = sessionManager.getSession(relatedIdentifier);
        return session.getGameData();
    }

    @Override
    public SessionIdentifier registerAvailableRaspBerry() {
        SessionIdentifier identifier = new SessionIdentifier();

        identifier.setId(sessionManager.registerRaspberryPiWithId());

        return identifier;
    }
}
