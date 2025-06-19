package com.retr0lbb.housekeeper.repository;

import com.retr0lbb.housekeeper.entitys.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
    List<SessionEntity> findAllByUserIdAndIsValidTrue(UUID userId);
}
