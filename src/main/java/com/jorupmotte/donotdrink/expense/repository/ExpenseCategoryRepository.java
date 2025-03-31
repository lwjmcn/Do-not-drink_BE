package com.jorupmotte.donotdrink.expense.repository;

import com.jorupmotte.donotdrink.expense.model.ExpenseCategory;
import com.jorupmotte.donotdrink.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseCategoryRepository  extends JpaRepository<ExpenseCategory, Long> {
    Optional<ExpenseCategory> findExpenseCategoryById(Long id);
    List<ExpenseCategory> findAllByUserIsNullOrUser(User user);
}
