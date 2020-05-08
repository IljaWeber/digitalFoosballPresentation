package com.valtech.digitalFoosball.service.game.init;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.storage.IObtainTeams;
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
