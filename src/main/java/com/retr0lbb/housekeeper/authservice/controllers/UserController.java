package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.entitys.UserModel;
import com.retr0lbb.housekeeper.authservice.services.UserService;
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
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user){
        UserModel savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
