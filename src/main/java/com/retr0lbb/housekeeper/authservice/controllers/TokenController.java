package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.authservice.dto.LoginRequest;
import com.retr0lbb.housekeeper.authservice.dto.LoginResponse;
import com.retr0lbb.housekeeper.entitys.RolesModel;
import com.retr0lbb.housekeeper.entitys.UserModel;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TokenController {
    private final JwtEncoder jwtEncoder;
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Optional<UserModel> user = this.userRepository.findByEmail(loginRequest.userName());

        if(user.isEmpty() ||  !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("User or password invalid");
        }

        var now = Instant.now();
        var expiresIn = 2 * 60 * 60;
        var scopes = user.get().getRoles().stream().map(RolesModel::getName).collect(Collectors.joining(" "));
        System.out.println("Esse eh o seu token seu bozo "+ scopes);

        var claims = JwtClaimsSet.builder()
                .issuer("House Keeper Backend 2025")
                .subject(user.get().getUserId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();



        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
