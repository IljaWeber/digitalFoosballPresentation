package com.valtech.digitalFoosball.storage.repository;


import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerDataModel, UUID> {
    Optional<PlayerDataModel> findByName(String name);
}