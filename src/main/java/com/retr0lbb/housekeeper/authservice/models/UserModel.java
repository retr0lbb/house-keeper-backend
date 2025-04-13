package com.retr0lbb.housekeeper.authservice.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;

    private String password;

    private String fullname;


    public UserModel() {
    }

    public UserModel(UUID id, String userName, String email, String password, String fullname) {
        super();
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

