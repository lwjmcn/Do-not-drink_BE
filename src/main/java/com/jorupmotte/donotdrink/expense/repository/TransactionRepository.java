package com.jorupmotte.donotdrink.expense.repository;

import com.jorupmotte.donotdrink.expense.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Transactional
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.user.id = :userId AND t.date BETWEEN :startDate AND :endDate")
    Long sumAllByUser_IdAndDateGreaterThanEqualAndDateLessThanEqual(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Transactional
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.id = :userId AND t.category.id = :categoryId AND t.date BETWEEN :startDate AND :endDate")
    Long sumAllByUser_IdAndCategory_IdAndDateGreaterThanEqualAndDateLessThanEqual(Long userId, Long categoryId, LocalDateTime startDate, LocalDateTime endDate);

    Page<Transaction> findAllByUser_IdAndCategory_Id(Long userId, Long categoryId, Pageable pageable);
}
