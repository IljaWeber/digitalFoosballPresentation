package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.api.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.junit.jupiter.api.Test;

class RankedAPIShould {

    @Test
    void inform_the_raspi_controller_that_the_current_game_is_ranked() {
        RankedGameRules game = new RankedGameRules(new FakeInitService());
        FakeRaspiController raspiController = new FakeRaspiController();


    }

    private class FakeRaspiController extends RaspiController {
        public IPlayAGame IPlayAGame;

        private FakeRaspiController() {
            super(null, null);
        }

        public void setGame(IPlayAGame IPlayAGame) {
            this.IPlayAGame = IPlayAGame;
        }
    }

    private class FakeInitService extends RankedInitService {
        public FakeInitService() {
            super(null);
        }

        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            return new GameDataModel();
        }
    }
}
