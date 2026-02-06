package com.engineai.tacl.auth.application.service;

import com.engineai.tacl.auth.domain.model.User;
import com.engineai.tacl.auth.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engineai.tacl.auth.infrastructure.exception.InvalidCredentialsException;

@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoginService(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String email, String rawPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches =
                passwordEncoder.matches(rawPassword, user.getPasswordHash());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        return user;
    }
}
