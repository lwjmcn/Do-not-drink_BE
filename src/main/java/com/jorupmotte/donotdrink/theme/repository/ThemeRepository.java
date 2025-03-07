package com.jorupmotte.donotdrink.theme.repository;

import com.jorupmotte.donotdrink.theme.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findById(Long id);
    boolean existsById(Long id);
    List<Theme> findAll();
}
