package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHocGame extends DigitalFoosballGame {

    @Autowired
    public AdHocGame(AdHocInitService initService) {
        super(initService);
    }
}
