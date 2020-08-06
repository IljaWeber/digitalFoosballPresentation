package com.valtech.digitalFoosball.api.persistence.repository;

import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends CrudRepository<TeamDataModel, UUID> {
    Optional<TeamDataModel> findByNameIgnoreCase(String teamName);

    List<TeamDataModel> findAll();
}
