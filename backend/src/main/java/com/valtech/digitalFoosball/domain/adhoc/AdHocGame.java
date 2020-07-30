package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.ranked.ClassicGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHocGame extends ClassicGame {

    @Autowired
    public AdHocGame(AdHocInitService initService) {
        super(initService);
    }
}
