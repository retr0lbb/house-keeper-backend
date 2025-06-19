package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.authservice.dto.LoginRequest;
import com.retr0lbb.housekeeper.authservice.dto.LoginResponse;
import com.retr0lbb.housekeeper.entitys.SessionEntity;
import com.retr0lbb.housekeeper.entitys.UserEntity;
import com.retr0lbb.housekeeper.repository.SessionRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TokenController {
    private final JwtEncoder jwtEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Optional<UserEntity> user = this.userRepository.findByEmail(loginRequest.userName());

        if(user.isEmpty() ||  !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("User or password invalid");
        }

        var now = Instant.now();
        var expiresIn = 2 * 60 * 60;
        var scope = user.get().getAccessLevel().getDescription();
        System.out.println("Esse eh o seu token seu bozo "+ scope);

        var claims = JwtClaimsSet.builder()
                .issuer("House Keeper Backend 2025")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        SessionEntity session = new SessionEntity();
        session.setUser(user.get());
        session.setIsValid(Boolean.TRUE);
        session.setExpiresIn(LocalDateTime.now().plusDays(60));
        sessionRepository.save(session);


        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
