package com.engineai.tacl.auth.infrastructure.controller;

import com.engineai.tacl.auth.application.service.UserLoginService;
import com.engineai.tacl.auth.application.service.UserRegistrationService;
import com.engineai.tacl.auth.domain.model.User;
import com.engineai.tacl.auth.domain.model.UserSession;
import com.engineai.tacl.auth.domain.repository.UserSessionRepository;
import com.engineai.tacl.auth.infrastructure.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRegistrationService registrationService;
    private final UserLoginService loginService;
    private final JwtService jwtService;
    private final UserSessionRepository userSessionRepository;

    public AuthController(UserRegistrationService registrationService,
                          UserLoginService loginService,
                          JwtService jwtService,
                          UserSessionRepository userSessionRepository) {
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.jwtService = jwtService;
        this.userSessionRepository = userSessionRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {

        User user = registrationService.register(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getAge(),
                request.getEmail(),
                request.getPassword()
        );

        RegisterResponse response = new RegisterResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getAge(),
                user.getEmail(),
                user.getStatus()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = loginService.login(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail()
        );

        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusHours(48));

        userSessionRepository.save(session);

        LoginResponse response = new LoginResponse(
                token,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}