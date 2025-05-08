package com.retr0lbb.housekeeper.authservice.services;

import com.retr0lbb.housekeeper.entitys.UserModel;
import com.retr0lbb.housekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }
}
