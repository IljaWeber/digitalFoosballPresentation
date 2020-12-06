package com.valtech.digitalFoosball.config;

import com.valtech.digitalFoosball.domain.DigitalFoosballFacade;
import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.IPlayDigitalFoosball;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
public class WebConfig {

    @Bean(name = "facade_bean")
    public IPlayDigitalFoosball iPlayDigitalFoosball(@Qualifier("ranked_init_service") IInitializeGames rankedInitService,
                                                     INotifyAboutStateChanges publisher) {
        return new DigitalFoosballFacade(rankedInitService, publisher);
    }

    @Bean(name = "ranked_init_service")
    public IInitializeGames iInitializeGames(RankedGamePersistencePort rankedGamePersistencePort) {
        return new RankedInitService(rankedGamePersistencePort);
    }
}
