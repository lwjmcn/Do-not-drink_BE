package com.jorupmotte.donotdrink.expense.service;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ExpenseType;
import com.jorupmotte.donotdrink.expense.dto.request.TransactionAddRequestDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionAddResponseDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionListInCategoryResponseDto;
import com.jorupmotte.donotdrink.expense.model.ExpenseCategory;
import com.jorupmotte.donotdrink.expense.model.Transaction;
import com.jorupmotte.donotdrink.expense.repository.ExpenseCategoryRepository;
import com.jorupmotte.donotdrink.expense.repository.TransactionRepository;
import com.jorupmotte.donotdrink.user.model.User;
import com.jorupmotte.donotdrink.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService implements ITransactionService{

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Transactional
    @Override
    public ResponseEntity<? super TransactionAddResponseDto> addTransaction(TransactionAddRequestDto requestDto) {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        Optional<ExpenseCategory> expenseCategoryOptional = expenseCategoryRepository.findExpenseCategoryById(requestDto.getCategoryId());
        if(expenseCategoryOptional.isEmpty()){
            return ResponseDto.databaseError();
        }

        Transaction transaction = Transaction.builder()
                .expenseType(ExpenseType.OUT)
                .user(userMe)
                .category(expenseCategoryOptional.get())
                .date(LocalDateTime.parse(requestDto.getDatetime()))
                .name(requestDto.getName())
                .amount((long) requestDto.getAmount())
                .description(requestDto.getDescription())
                .build();
        transactionRepository.save(transaction);

        return TransactionAddResponseDto.success();
    }

    @Override
    public ResponseEntity<? super TransactionListInCategoryResponseDto> getTransactionsInCategory(Long categoryId, Pageable pageable) {
        User userMe = userService.getUserFromSecurityContext();
        if(userMe == null) {
            return ResponseDto.authorizationFail();
        }

        Optional<ExpenseCategory> expenseCategoryOptional = expenseCategoryRepository.findExpenseCategoryById(categoryId);
        if(expenseCategoryOptional.isEmpty()){
            return ResponseDto.databaseError();
        }

        Page<Transaction> pagedTransactions = transactionRepository.findAllByUser_IdAndCategory_Id(userMe.getId(), categoryId, pageable);

        return TransactionListInCategoryResponseDto.success(expenseCategoryOptional.get().getName(), pagedTransactions);
    }
}
