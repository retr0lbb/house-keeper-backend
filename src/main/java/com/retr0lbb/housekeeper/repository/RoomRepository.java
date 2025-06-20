package com.retr0lbb.housekeeper.repository;

import com.retr0lbb.housekeeper.entitys.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
}
