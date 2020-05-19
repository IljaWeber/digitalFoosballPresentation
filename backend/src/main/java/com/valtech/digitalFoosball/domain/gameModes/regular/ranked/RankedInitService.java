package com.valtech.digitalFoosball.domain.gameModes.regular.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractInitService;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedInitService extends AbstractInitService {

    @Autowired
    public RankedInitService(IObtainTeams teamDataPort) {
        super(teamDataPort);
    }

    public GameDataModel init(InitDataModel initDataModel) {
        UniqueNameVerifier uniqueNameVerifier = new UniqueNameVerifier();
        uniqueNameVerifier.checkForDuplicateNames(initDataModel);

        return prepare(initDataModel);
    }
}
