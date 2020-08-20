package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedGameRules extends DigitalFoosballGameRules {

    @Autowired
    public RankedGameRules(RankedInitService initService) {
        super(initService);
    }
}
