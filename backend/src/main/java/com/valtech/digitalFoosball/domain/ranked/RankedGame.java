package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedGame extends DigitalFoosballGame {

    @Autowired
    public RankedGame(RankedInitService initService) {
        super(initService);
    }
}
