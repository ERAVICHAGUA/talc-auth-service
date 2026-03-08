package com.engineai.tacl.auth.application.service;

import com.engineai.tacl.auth.domain.model.User;
import com.engineai.tacl.auth.domain.repository.UserRepository;
import com.engineai.tacl.auth.infrastructure.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String firstName,
                         String lastName,
                         String username,
                         Integer age,
                         String email,
                         String rawPassword) {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("El username ya está registrado");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setAge(age);
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}