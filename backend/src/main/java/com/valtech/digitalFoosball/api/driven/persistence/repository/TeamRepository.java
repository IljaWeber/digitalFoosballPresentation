package com.valtech.digitalFoosball.api.driven.persistence.repository;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends CrudRepository<RankedTeamDataModel, UUID> {
    Optional<RankedTeamDataModel> findByNameIgnoreCase(String teamName);

    List<RankedTeamDataModel> findAll();
}
