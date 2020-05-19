package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractInitService;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeGameInitService extends AbstractInitService {

    @Autowired
    public TimeGameInitService(IObtainTeams teamDataPort) {
        super(teamDataPort);
    }

    @Override
    public GameDataModel init(InitDataModel initDataModel) {
        return prepare(initDataModel);
    }
}
