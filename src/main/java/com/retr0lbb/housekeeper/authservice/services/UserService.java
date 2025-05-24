package com.retr0lbb.housekeeper.authservice.services;

import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.entitys.UserModel;
import com.retr0lbb.housekeeper.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel saveUser(CreateUserDTO user) throws BadRequestException {
        var userFromDb = userRepository.findByEmail(user.email());

        if(userFromDb.isPresent()){
            throw new BadRequestException("UserAlready Exists");
        }
        var newUser = new UserModel();
        newUser.setFullName(user.fullName());
        newUser.setEmail(user.email());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        newUser.setUserName(user.userName());

        return userRepository.save(newUser);
    }
}
