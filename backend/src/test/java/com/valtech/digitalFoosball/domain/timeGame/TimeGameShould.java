package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;
import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class TimeGameShould {

    @Test
    public void start_a_time_game() {
        IPlayAGame timeIPlayAGame = new TimeGame(new AdHocInitService());

        timeIPlayAGame.initGame(new InitDataModel());

        timeIPlayAGame.countGoalFor(ONE);
        GameOutputModel outputModel = timeIPlayAGame.getGameData();
        List<TeamOutputModel> actual = outputModel.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(1, 0);
        assertThat(outputModel.getMatchWinner()).isEqualTo(NO_TEAM);
    }
}
