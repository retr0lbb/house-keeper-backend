package com.retr0lbb.housekeeper.repository;

import com.retr0lbb.housekeeper.entitys.AccessLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<AccessLevelEntity, Long> { ;
    Optional<AccessLevelEntity> findByDescription(String name);
}
