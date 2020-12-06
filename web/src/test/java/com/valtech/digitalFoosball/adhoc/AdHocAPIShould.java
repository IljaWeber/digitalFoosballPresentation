package com.valtech.digitalFoosball.adhoc;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import com.valtech.digitalFoosball.helper.ComparableOutputModelCreator;
import com.valtech.digitalFoosball.helper.RestEndpointRequestPerformer;
import com.valtech.digitalFoosball.rest.AdHocAPI;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = AdHocAPI.class)
@SpringBootConfiguration
@Import(RestEndpointRequestPerformer.class)
public class AdHocAPIShould {
    ObjectMapper mapper;
    ComparableOutputModelCreator comparableOutput;

    @Autowired
    RestEndpointRequestPerformer endpointRequestPerformer;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        comparableOutput = new ComparableOutputModelCreator();
    }

    @Test
    void initialise_an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        comparableOutput = new ComparableOutputModelCreator();
        comparableOutput.prepareCompareTeamOneWithValues("Orange",
                                                         "Goalie", "Striker");
        comparableOutput.prepareCompareTeamTwoWithValues("Green",
                                                         "Goalie", "Striker");
        String expected
                = mapper.writeValueAsString(comparableOutput);

        String raspberryPi = endpointRequestPerformer.registerRaspberryPi();
        SessionIdentifier identifier = new SessionIdentifier();
        identifier.setId(UUID.fromString(raspberryPi));
        endpointRequestPerformer.prepareAdHocInitialization(identifier);

        endpointRequestPerformer.initializeGame(GameMode.AD_HOC);

        String actual = endpointRequestPerformer.getGameValues(GameMode.AD_HOC, identifier);
        assertThat(actual).isEqualTo(expected);
    }
}
