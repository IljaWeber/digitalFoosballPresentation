package com.valtech.digitalFoosball.domain.ranked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedGame extends ClassicGame {

    @Autowired
    public RankedGame(RankedInitService initService) {
        super(initService);
    }
}
