package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.budget.dto.request.BudgetSetRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetRemainingResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetSetResponseDto;
import com.jorupmotte.donotdrink.budget.model.Budget;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IBudgetService {
    Optional<Budget> getCurrentBudget(Long userId);
    float getRemainingRate(Long userId);
    ResponseEntity<? super BudgetRemainingResponseDto> getRemainingBudget();
    ResponseEntity<? super BudgetSetResponseDto> setBudget(BudgetSetRequestDto requestDto);
}
