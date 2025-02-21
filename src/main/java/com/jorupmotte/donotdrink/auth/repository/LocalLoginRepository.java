package com.jorupmotte.donotdrink.auth.repository;

import com.jorupmotte.donotdrink.auth.model.LocalLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalLoginRepository extends JpaRepository<LocalLogin, Long> {
    Optional<LocalLogin> getByEmail(String email);
    boolean existsByEmail(String email);
}
