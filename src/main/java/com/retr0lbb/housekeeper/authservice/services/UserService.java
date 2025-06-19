package com.retr0lbb.housekeeper.authservice.services;

import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.entitys.UserEntity;
import com.retr0lbb.housekeeper.repository.RoleRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity saveUser(CreateUserDTO user) throws Exception {
        var userFromDb = userRepository.findByEmail(user.email());
        var accessLevel = roleRepository.findByDescription("basic");

        if(accessLevel.isEmpty()){
            throw new Exception("Cannot find role");
        }

        if(userFromDb.isPresent()){
            throw new BadRequestException("UserAlready Exists");
        }
        var newUser = new UserEntity();
        newUser.setName(user.fullName());
        newUser.setEmail(user.email());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        newUser.setAccessLevel(accessLevel.get());

        return userRepository.save(newUser);
    }


    public List<UserEntity> getAll(){
        return this.userRepository.findAll();
    }
}
