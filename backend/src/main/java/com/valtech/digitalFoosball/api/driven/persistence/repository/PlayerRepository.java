package com.valtech.digitalFoosball.api.driven.persistence.repository;

import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerDataModel, UUID> {
    Optional<PlayerDataModel> findByName(String name);
}
