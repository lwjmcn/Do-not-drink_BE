package com.jorupmotte.donotdrink.expense.service;

import com.jorupmotte.donotdrink.budget.model.Budget;
import com.jorupmotte.donotdrink.budget.service.BudgetService;
import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.expense.dto.response.CategoryListResponseDto;
import com.jorupmotte.donotdrink.expense.model.ExpenseCategory;
import com.jorupmotte.donotdrink.expense.repository.ExpenseCategoryRepository;
import com.jorupmotte.donotdrink.expense.repository.TransactionRepository;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService implements ICategoryService {
    private final UserService userService;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetService budgetService;

    @Override
    public Long getTransactionSumByUserCategory(Long userId, Long categoryId) {
        Optional<Budget> budgetOptional = budgetService.getCurrentBudget(userId);
        if(budgetOptional.isEmpty()){
            return 0L;
        }
        LocalDateTime startDate = budgetOptional.get().getStartDate();
        LocalDateTime endDate = budgetOptional.get().getEndDate();

        return transactionRepository.sumAllByUser_IdAndCategory_IdAndDateGreaterThanEqualAndDateLessThanEqual(
                userId,
                categoryId,
                startDate,
                endDate
        );
    }

    @Override
    public ResponseEntity<? super CategoryListResponseDto> getCategories() {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        List<ExpenseCategory> expenseCategories =expenseCategoryRepository.findAllByUserIsNullOrUser(userMe);
        List<CategoryListResponseDto.CategoryDto> categories = expenseCategories.stream()
                .map(category ->
                        CategoryListResponseDto.CategoryDto.from(
                                category,
                                getTransactionSumByUserCategory(userMe.getId(),category.getId())

                        )
                )
                .sorted(CategoryListResponseDto.CategoryDto::compareTo)
                .toList();

        return CategoryListResponseDto.success(categories);
    }


}
