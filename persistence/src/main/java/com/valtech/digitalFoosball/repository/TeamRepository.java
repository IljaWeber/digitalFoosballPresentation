package com.valtech.digitalFoosball.repository;

import com.valtech.digitalFoosball.entity.TeamDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends CrudRepository<TeamDataEntity, UUID> {

    Optional<TeamDataEntity> findByNameIgnoreCase(String teamName);

    List<TeamDataEntity> findAll();
}
