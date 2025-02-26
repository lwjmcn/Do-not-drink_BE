package com.jorupmotte.donotdrink.auth.repository;

import com.jorupmotte.donotdrink.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    // get default
    Optional<Theme> findById(Long id);
    boolean existsById(Long id);
}
