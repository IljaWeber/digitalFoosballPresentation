package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.ClassicGameInitService;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedInitService extends ClassicGameInitService {

    @Autowired
    public RankedInitService(IObtainTeams teamDataPort) {
        super(teamDataPort);
    }

    public RankedGameDataModel init(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        return prepare(initDataModel);
    }

    protected RankedGameDataModel prepare(InitDataModel initDataModel) {

        return super.prepare(initDataModel);
    }
}
