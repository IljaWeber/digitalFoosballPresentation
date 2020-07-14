package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.adhoc.ClassicGame;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankedGame extends ClassicGame {
    private final RankedInitService initService;

    @Autowired
    public RankedGame(RankedInitService initService) {
        this.initService = initService;
    }

    @Override
    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    public void initGame(InitDataModel initDataModel) {
        gameDataModel = initService.init(initDataModel);
    }
}
