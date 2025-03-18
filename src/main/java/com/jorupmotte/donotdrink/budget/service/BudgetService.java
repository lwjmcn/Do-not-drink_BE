package com.jorupmotte.donotdrink.budget.service;

import com.jorupmotte.donotdrink.budget.dto.request.BudgetSetRequestDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetRemainingResponseDto;
import com.jorupmotte.donotdrink.budget.dto.response.BudgetSetResponseDto;
import com.jorupmotte.donotdrink.budget.model.Budget;
import com.jorupmotte.donotdrink.budget.repository.BudgetRepository;
import com.jorupmotte.donotdrink.budget.repository.TransactionRepository;
import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService implements IBudgetService {
    private final UserService userService;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<? super BudgetRemainingResponseDto> getRemainingBudget() {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 현재 budget 조회
        LocalDateTime now = LocalDateTime.now();
        Optional<Budget> budgetOptional = budgetRepository.findByUser_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userMe.getId(), now, now);
        if(budgetOptional.isEmpty()){
            return BudgetRemainingResponseDto.noBudget();
        }
        Long budget = budgetOptional.get().getAmount();
        LocalDateTime startDate = budgetOptional.get().getStartDate();
        LocalDateTime endDate = budgetOptional.get().getEndDate();

        // 현재 사용량 조회
        Long used = transactionRepository.sumAllByUser_IdAndDateGreaterThanEqualAndDateLessThanEqual(userMe.getId(), startDate, endDate);

        // 남은 금액 계산
        Long remains = budget - used;

        return BudgetRemainingResponseDto.success(remains);
    }

    @Override
    public ResponseEntity<? super BudgetSetResponseDto> setBudget(BudgetSetRequestDto requestDto) {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 현재 budget 조회
        LocalDateTime now = LocalDateTime.now();
        Optional<Budget> budgetOptional = budgetRepository.findByUser_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userMe.getId(), now, now);
        if(budgetOptional.isPresent()){
            return BudgetSetResponseDto.alreadyDefined();
        }

        budgetRepository.save(Budget.builder()
                .user(userMe)
                .amount(requestDto.getBudget())
                .startDate(now)
                .endDate(now)
                .build());

        return BudgetSetResponseDto.success();
    }
}
