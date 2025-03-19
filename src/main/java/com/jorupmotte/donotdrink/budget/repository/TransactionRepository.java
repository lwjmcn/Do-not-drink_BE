package com.jorupmotte.donotdrink.budget.repository;

import com.jorupmotte.donotdrink.budget.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Transactional
    @Query("SELECT IFNULL(SUM(t.amount),0) FROM Transaction t WHERE t.user.id = ?1 AND t.date >= ?2 AND t.date <= ?3")
    Long sumAllByUser_IdAndDateGreaterThanEqualAndDateLessThanEqual(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}
