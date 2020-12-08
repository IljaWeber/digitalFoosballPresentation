package com.valtech.digitalFoosball.domain.usecases.ranked;

import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;

public class RankedGameRules extends DigitalFoosballGameRules {

    public RankedGameRules(IInitializeGames initService) {
        super(initService);
    }
}
