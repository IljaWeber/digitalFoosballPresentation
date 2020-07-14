package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHocInitService extends ClassicGameInitService {

    @Autowired
    public AdHocInitService(IObtainTeams teamDataPort) {
        super(teamDataPort);
    }

    public RankedGameDataModel init(InitDataModel initDataModel) {
        return prepare(initDataModel);
    }
}
