package com.jorupmotte.donotdrink.auth.repository;

import com.jorupmotte.donotdrink.auth.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    // sorted by createdAt, get the latest one
    Optional<Verification> findTopByLocalLoginIdOrderByCreatedAtDesc(Long localLoginId);
}
