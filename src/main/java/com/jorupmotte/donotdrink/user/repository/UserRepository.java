package com.jorupmotte.donotdrink.user.repository;

import com.jorupmotte.donotdrink.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccountId(String accountId);
    boolean existsByAccountId(String accountId);
}
