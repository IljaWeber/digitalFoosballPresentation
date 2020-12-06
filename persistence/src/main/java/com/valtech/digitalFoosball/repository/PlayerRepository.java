package com.valtech.digitalFoosball.repository;

import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerDataEntity, UUID> {
    Optional<PlayerDataEntity> findByName(String name);
}
