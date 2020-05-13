package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RegularGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameManipulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameManipulatorShould {
    public RankedGameManipulator game = new RankedGameManipulator();

    private final GameDataModel gameData = new RegularGameDataModel();

    private void setUpTeams() {
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        List<TeamDataModel> teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);
        gameData.setTeams(teams);
    }

    private List<TeamDataModel> getTeamDataModels() {
        SortedMap<Team, TeamDataModel> teams = gameData.getTeams();
        List<TeamDataModel> actual = new ArrayList<>();
        teams.forEach((k, v) -> actual.add(v));
        return actual;
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        game.changeover(gameData);

        List<TeamDataModel> teams = getTeamDataModels();
        assertThat(teams).extracting(TeamDataModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamDataModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamDataModel::getNameOfPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamDataModel::getNameOfPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void forget_about_shot_goals_from_the_past_set() {
        setUpTeams();
        raiseScoreOf(ONE);
        game.undoGoal(gameData);
        game.changeover(gameData);

        game.undoGoal(gameData);

        TeamDataModel team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void forget_about_undid_goals_from_the_past_set() {
        setUpTeams();
        raiseScoreOf(ONE);
        game.undoGoal(gameData);
        game.changeover(gameData);

        game.redoGoal(gameData);

        TeamDataModel team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(0);
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team, gameData);
        }
    }
}