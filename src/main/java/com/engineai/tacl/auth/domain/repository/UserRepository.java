package com.engineai.tacl.auth.domain.repository;

import com.engineai.tacl.auth.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email); // ESTA LÍNEA ES LA CLAVE
}
