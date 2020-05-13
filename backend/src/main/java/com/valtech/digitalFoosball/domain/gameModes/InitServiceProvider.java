package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.InitialService;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitServiceProvider {
    private final RankedInitService rankedInitService;
    private final AdHocInitService adHocInitService;

    @Autowired
    public InitServiceProvider(RankedInitService rankedInitService,
                               AdHocInitService adHocInitService) {
        this.rankedInitService = rankedInitService;
        this.adHocInitService = adHocInitService;
    }

    public InitialService getInitService(GameMode mode) {
        switch (mode) {
            case RANKED:
                return rankedInitService;

            default:
                return adHocInitService;
        }
    }
}
