package com.valtech.digitalFoosball.domain.usecases.adhoc;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;

public class AdHocGameRules extends DigitalFoosballGameRules {

    public AdHocGameRules(AdHocInitService initService) {
        super(initService);
    }
}
