package com.valtech.digitalFoosball.config;

import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import com.valtech.digitalFoosball.entity.TeamDataEntity;
import com.valtech.digitalFoosball.repository.PlayerRepository;
import com.valtech.digitalFoosball.repository.TeamRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {TeamRepository.class, PlayerRepository.class})
@EntityScan(basePackageClasses = {PlayerDataEntity.class, TeamDataEntity.class})
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = "com.valtech.digitalFoosball")
public class PersistenceConfig {

}
