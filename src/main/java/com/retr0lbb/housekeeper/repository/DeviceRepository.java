package com.retr0lbb.housekeeper.repository;

import com.retr0lbb.housekeeper.entitys.DeviceEntity;
import com.retr0lbb.housekeeper.entitys.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    List<DeviceEntity> findByuser(UserModel user);
}
