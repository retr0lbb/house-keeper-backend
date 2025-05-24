package com.retr0lbb.housekeeper.entitys;

import com.retr0lbb.housekeeper.authservice.dto.LoginRequest;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String email;
    private String password;
    private String fullName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")

    )
    private Set<RolesModel> roles;


    public UserModel() {
    }

    public UserModel(UUID id, String userName, String email, String password, String fullname) {
        super();
        this.userId = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.fullName = fullname;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}

