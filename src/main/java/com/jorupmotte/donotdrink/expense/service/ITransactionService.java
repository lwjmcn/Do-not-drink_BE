package com.jorupmotte.donotdrink.expense.service;

import com.jorupmotte.donotdrink.expense.dto.request.TransactionAddRequestDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionAddResponseDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionListInCategoryResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {
    public ResponseEntity<? super TransactionAddResponseDto> addTransaction(TransactionAddRequestDto requestDto);
    public ResponseEntity<? super TransactionListInCategoryResponseDto> getTransactionsInCategory(Long categoryId, Pageable pageable);

}
