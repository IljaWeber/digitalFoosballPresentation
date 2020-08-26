package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;

public class RankedGameRules extends DigitalFoosballGameRules {

    public RankedGameRules(RankedInitService initService) {
        super(initService);
    }
}
