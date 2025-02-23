package com.jorupmotte.donotdrink.auth.repository;

import com.jorupmotte.donotdrink.auth.model.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
    Optional<SocialLogin> findByTokenId(String tokenId);
}
