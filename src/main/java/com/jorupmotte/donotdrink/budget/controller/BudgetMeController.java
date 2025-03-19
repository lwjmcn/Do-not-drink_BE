package com.jorupmotte.donotdrink.budget.controller;

import com.jorupmotte.donotdrink.budget.dto.request.BudgetSetRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetRemainingResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetSetResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.ReactionCurrentResponseDto;
import com.jorupmotte.donotdrink.budget.service.BudgetService;
import com.jorupmotte.donotdrink.budget.service.ReactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/budgets")
public class BudgetMeController {
    private final BudgetService budgetService;
    private final ReactionService reactionService;

    @GetMapping("/current/remains")
    public ResponseEntity<? super BudgetRemainingResponseDto> getRemainingBudget() {
        return budgetService.getRemainingBudget();
    }

    @PostMapping("/current")
    public ResponseEntity<? super BudgetSetResponseDto> setBudget(
            @RequestBody @Valid BudgetSetRequestDto requestBody
    ) {
        return budgetService.setBudget(requestBody);
    }

    @GetMapping(value = "/current/reactions")
    public ResponseEntity<? super ReactionCurrentResponseDto> getMyCurrentReactions() {
        return reactionService.getMyCurrentReactionsFromDb();
    }
}
