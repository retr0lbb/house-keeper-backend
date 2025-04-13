package com.retr0lbb.housekeeper.authservice.repository;

import com.retr0lbb.housekeeper.authservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}
