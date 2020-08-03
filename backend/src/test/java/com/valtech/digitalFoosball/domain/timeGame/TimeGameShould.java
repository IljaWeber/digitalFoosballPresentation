package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

class TimeGameShould {

    private TimeGame timeGame;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGame(new AdHocInitService());
        timeGame.initGame(new InitDataModel());
    }

    @Test
    public void start_a_time_game() {
        timeGame.initGame(new InitDataModel());

        GameOutputModel outputModel = timeGame.getGameData();
        List<TeamOutputModel> actual = outputModel.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
        assertThat(outputModel.getMatchWinner()).isEqualTo(NO_TEAM);
    }

    @Test
    public void prepare_a_game_in_first_half_with_team_and_player_names_with_their_scores_for_output() {
        raiseScoreFor(ONE, ONE,
                      TWO);

        TimeGameOutputModel gameData = timeGame.getGameData();
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(2, 1);
        assertThat(gameData.getGameState()).isEqualTo(GameState.FIRST_HALF);
        assertThat(gameData.getMatchWinner()).isEqualTo(NO_TEAM);
    }

    @Test
    public void show_game_which_has_won_by_reaching_the_score_limit_with_the_winner_all_team_and_player_names_and_their_scores() {
        raiseScoreFor(ONE, ONE,
                      ONE, ONE,
                      ONE, ONE,
                      ONE, ONE,
                      ONE, ONE);

        TimeGameOutputModel gameData = timeGame.getGameData();
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(10, 0);
        assertThat(gameData.getGameState()).isEqualTo(GameState.OVER_BY_SCORE);
        assertThat(gameData.getMatchWinner()).isEqualTo(ONE);
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team);
        }
    }
}
