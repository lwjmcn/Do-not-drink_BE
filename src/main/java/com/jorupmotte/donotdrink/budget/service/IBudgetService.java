package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.budget.dto.request.BudgetSetRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetRemainingResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetSetResponseDto;
import org.springframework.http.ResponseEntity;

public interface IBudgetService {
    ResponseEntity<? super BudgetRemainingResponseDto> getRemainingBudget();
    ResponseEntity<? super BudgetSetResponseDto> setBudget(BudgetSetRequestDto requestDto);
}
