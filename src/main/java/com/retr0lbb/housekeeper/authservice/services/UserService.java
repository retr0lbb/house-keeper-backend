package com.retr0lbb.housekeeper.authservice.services;

import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.entitys.UserModel;
import com.retr0lbb.housekeeper.repository.RoleRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel saveUser(CreateUserDTO user) throws Exception {
        var userFromDb = userRepository.findByEmail(user.email());
        var basicRole = roleRepository.findById(2L);

        if(basicRole.isEmpty()){
            throw new Exception("Cannot find role");
        }

        if(userFromDb.isPresent()){
            throw new BadRequestException("UserAlready Exists");
        }
        var newUser = new UserModel();
        newUser.setFullName(user.fullName());
        newUser.setEmail(user.email());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        newUser.setUserName(user.userName());
        newUser.setRoles(Set.of(basicRole.get()));

        return userRepository.save(newUser);
    }
}
