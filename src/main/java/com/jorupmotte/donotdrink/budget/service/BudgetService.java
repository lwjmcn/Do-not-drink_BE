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
    public Optional<Budget> getCurrentBudget(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return budgetRepository.findByUser_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userId, now, now);
    }

    @Override
    public float getRemainingRate(Long userId) {
        // 현재 budget 조회
        Optional<Budget> budgetOptional = getCurrentBudget(userId);
        if(budgetOptional.isEmpty()){
            return 0;
        }
        Long budget = budgetOptional.get().getAmount();

        LocalDateTime startDate = budgetOptional.get().getStartDate();
        LocalDateTime endDate = budgetOptional.get().getEndDate();

        // 현재 사용량 조회
        Long used = transactionRepository.sumAllByUser_IdAndDateGreaterThanEqualAndDateLessThanEqual(userId, startDate, endDate);

        // 남은 비율
        return (float) (budget - used) / budget * 100;
    }

    @Override
    public ResponseEntity<? super BudgetRemainingResponseDto> getRemainingBudget() {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        // 현재 budget 조회
        Optional<Budget> budgetOptional = getCurrentBudget(userMe.getId());
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
        Optional<Budget> budgetOptional = getCurrentBudget(userMe.getId());
        if(budgetOptional.isPresent()){
            return BudgetSetResponseDto.alreadyDefined();
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastDayOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth());
        System.out.println("lastDayOfThisMonth = " + lastDayOfThisMonth);

        Budget budget = Budget.builder()
                .user(userMe)
                .amount(requestDto.getBudget())
                .startDate(now)
                .endDate(lastDayOfThisMonth)
                .build();
        budgetRepository.save(budget);

        return BudgetSetResponseDto.success();
    }
}
