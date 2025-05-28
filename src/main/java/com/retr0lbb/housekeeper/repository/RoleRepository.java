package com.retr0lbb.housekeeper.repository;

import com.retr0lbb.housekeeper.entitys.RolesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<RolesModel, Long> { ;
    Optional<RolesModel> findByName(String name);
}
