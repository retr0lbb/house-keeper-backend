package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.authservice.models.UserModel;
import com.retr0lbb.housekeeper.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user){
        UserModel savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
