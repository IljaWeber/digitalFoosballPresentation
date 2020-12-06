package com.valtech.digitalFoosball.rest;

import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data")
public class
DigitalFoosballGetDataAPI {

    private final RankedGamePersistencePort teamDataPort;

    @Autowired
    public DigitalFoosballGetDataAPI(RankedGamePersistencePort teamDataPort) {
        this.teamDataPort = teamDataPort;
    }

    @GetMapping(path = "/allTeams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamOutputModel> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamDataPort.getAllTeamsFromDatabase();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return Converter.convertListToTeamOutputs(teamDataModels);
    }
}
