package com.engineai.tacl.auth.application.service;

import com.engineai.tacl.auth.domain.model.User;
import com.engineai.tacl.auth.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.engineai.tacl.auth.infrastructure.exception.UserAlreadyExistsException;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String rawPassword) {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

}
