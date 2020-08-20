package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHocGameRules extends DigitalFoosballGameRules {

    @Autowired
    public AdHocGameRules(AdHocInitService initService) {
        super(initService);
    }
}
