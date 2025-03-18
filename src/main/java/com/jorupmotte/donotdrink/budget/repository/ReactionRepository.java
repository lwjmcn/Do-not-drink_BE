package com.jorupmotte.donotdrink.budget.repository;

import com.jorupmotte.donotdrink.budget.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findAllByBudget_Id(Long budgetId);
}
