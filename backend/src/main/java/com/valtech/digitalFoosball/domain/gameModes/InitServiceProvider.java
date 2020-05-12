package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.InitialService;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitServiceProvider {
    private RankedInitService rankedInitService;
    private AdHocInitService adHocInitService;

    @Autowired
    public InitServiceProvider(RankedInitService rankedInitService,
                               AdHocInitService adHocInitService) {
        this.rankedInitService = rankedInitService;
        this.adHocInitService = adHocInitService;
    }

    public InitialService getInitService(InitDataModel initDataModel) {
        GameMode mode = initDataModel.getMode();

        switch (mode) {
            case RANKED:
                return rankedInitService;

            default:
                return adHocInitService;
        }
    }
}
