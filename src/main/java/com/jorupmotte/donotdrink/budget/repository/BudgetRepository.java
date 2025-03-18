package com.jorupmotte.donotdrink.budget.repository;

import com.jorupmotte.donotdrink.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUser_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
