package com.valtech.digitalFoosball.domain.timePlay;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.AbstractInitService;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
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
