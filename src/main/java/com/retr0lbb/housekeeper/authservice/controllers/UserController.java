package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.authservice.services.UserService;
import com.retr0lbb.housekeeper.entitys.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserDTO user){
        try{
            UserEntity savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping
    public List<UserEntity> getAll(){
        return this.userService.getAll();
    }
}
