package com.jorupmotte.donotdrink.budget.repository;

import com.jorupmotte.donotdrink.budget.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Long sumAllByUser_IdAndDateGreaterThanEqualAndDateLessThanEqual(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}
