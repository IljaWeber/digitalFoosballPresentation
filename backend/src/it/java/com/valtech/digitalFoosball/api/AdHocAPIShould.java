package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.AdHocAPI;
import org.assertj.core.api.Assertions;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.AD_HOC;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = AdHocAPI.class)
@SpringBootConfiguration
@Import(com.valtech.digitalFoosball.api.RestEndpointRequestPerformer.class)
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

        endpointRequestPerformer.initializeGame(AD_HOC);

        String actual
                = endpointRequestPerformer.getGameValues(AD_HOC);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}