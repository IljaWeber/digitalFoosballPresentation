package com.valtech.digitalFoosball.config;

import com.valtech.digitalFoosball.domain.DigitalFoosballFacade;
import com.valtech.digitalFoosball.domain.IPlayDigitalFoosball;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
public class WebConfig {

    @Bean(name = "facade_bean")
    public IPlayDigitalFoosball iPlayDigitalFoosball(RankedInitService rankedInitService,
                                                     INotifyAboutStateChanges publisher) {
        return new DigitalFoosballFacade(rankedInitService, new AdHocInitService(), publisher);
    }

    @Bean
    public RankedInitService iInitializeGames(RankedGamePersistencePort rankedGamePersistencePort) {
        return new RankedInitService(rankedGamePersistencePort);
    }
}
