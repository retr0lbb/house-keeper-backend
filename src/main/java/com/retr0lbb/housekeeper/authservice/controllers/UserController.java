package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.authservice.dto.CreateUserDTO;
import com.retr0lbb.housekeeper.entitys.UserModel;
import com.retr0lbb.housekeeper.authservice.services.UserService;
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
    public ResponseEntity<UserModel> createUser(@RequestBody CreateUserDTO user){
        try{
            UserModel savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @PutMapping("/promote/{id}")
    @PreAuthorize("hasAuthority('SCOPE_basic')")
    public ResponseEntity<String> promoteBasicToAdmin(@PathVariable UUID id, JwtAuthenticationToken token){
        try{
            this.userService.promoteUser(id);
            return ResponseEntity.ok("User saved successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<UserModel> getAll(){
        return this.userService.getAll();
    }
}
