package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.timeGame.TimeGame;
import org.junit.jupiter.api.Test;

public class TimeGameAPIShould {

    @Test
    void inform_the_raspi_controller_that_the_current_game_is_adhoc() {
        TimeGame game = new TimeGame(new FakeInitService(), new FakePublisher());
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

    private class FakeInitService extends AdHocInitService {
        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            return new GameDataModel();
        }
    }

    private class FakePublisher implements INotifyAboutStateChanges {

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
        }
    }
}
