package com.retr0lbb.housekeeper.repository;

import com.retr0lbb.housekeeper.entitys.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    Page<DeviceEntity> findByUserId(UUID userId, Pageable pageable);
    Page<DeviceEntity> findByDeviceSlugContainingIgnoreCase(String query, Pageable pageable);
    Page<DeviceEntity> findByUserIdAndDeviceSlugContainingIgnoreCase(UUID userId, String query, Pageable pageable);
}
