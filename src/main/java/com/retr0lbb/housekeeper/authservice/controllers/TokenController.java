package com.retr0lbb.housekeeper.authservice.controllers;

import com.retr0lbb.housekeeper.authservice.dto.LoginRequest;
import com.retr0lbb.housekeeper.authservice.dto.LoginResponse;
import com.retr0lbb.housekeeper.entitys.SessionEntity;
import com.retr0lbb.housekeeper.entitys.UserEntity;
import com.retr0lbb.housekeeper.repository.SessionRepository;
import com.retr0lbb.housekeeper.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        var scope = user.get().getAccessLevel().getDescription();

        var claims = JwtClaimsSet.builder()
                .issuer("House Keeper Backend 2025")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(600))
                .claim("scope", scope)
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        SessionEntity session = new SessionEntity();
        session.setUser(user.get());
        session.setIsValid(Boolean.TRUE);
        session.setExpiresIn(LocalDateTime.now().plusDays(60));
        SessionEntity savedSession = sessionRepository.save(session);

        UUID refresh_token = savedSession.getId();

        //Cookie
        ResponseCookie cookie = ResponseCookie.from("HK_refresh_token", refresh_token.toString())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60*60*24*60)
                .sameSite("Strict")
                .build();



        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new LoginResponse(jwtValue, refresh_token.toString(), 10));
    }

    @GetMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@CookieValue(name = "HK_refresh_token", required = false) String refreshTokenValue){
        if(refreshTokenValue == null){
            System.out.println("Memes");
            return ResponseEntity.status(401).build();
        }

        UUID refreshToken;

        try{
            refreshToken = UUID.fromString(refreshTokenValue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<SessionEntity> sessionOpt = sessionRepository.findById(refreshToken);

        if (sessionOpt.isEmpty() || !sessionOpt.get().getIsValid() || sessionOpt.get().getExpiresIn().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(401).build();
        }

        UserEntity user = sessionOpt.get().getUser();

        var now = Instant.now();
        var expiresIn = 600; //10 minutes
        var scope = user.getAccessLevel().getDescription();

        var claims = JwtClaimsSet.builder()
                .issuer("House Keeper Backend 2025")
                .subject(user.getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .issuedAt(now)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, refreshTokenValue, expiresIn));

    }

    @PostMapping("/loggout")
    public ResponseEntity<Void> loggout(JwtAuthenticationToken token){
        UUID userId = UUID.fromString(token.getName());

        var user = userRepository.findById(userId);

        if(user.isEmpty()){
            return ResponseEntity.status(404).build();
        }


        List<SessionEntity> validSessions = sessionRepository.findAllByUserIdAndIsValidTrue(user.get().getId());

        if(validSessions.isEmpty()){
            System.out.println("Nenhuma sessao encontrada ok");
            return ResponseEntity.ok().build();
        }

        for (SessionEntity session : validSessions) {
            session.setIsValid(false);
        }

        sessionRepository.saveAll(validSessions);


        System.out.println("Logout concluído, todas as sessões foram invalidadas.");

        return ResponseEntity.ok().build();

    }

}
