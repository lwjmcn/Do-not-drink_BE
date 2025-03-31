package com.jorupmotte.donotdrink.expense.controller;

import com.jorupmotte.donotdrink.expense.dto.request.TransactionAddRequestDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionAddResponseDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionListInCategoryResponseDto;
import com.jorupmotte.donotdrink.expense.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<? super TransactionAddResponseDto> addTransaction(
            @RequestBody @Valid TransactionAddRequestDto requestBody
    ){
        return transactionService.addTransaction(requestBody);
    }

    @GetMapping("/")
    public ResponseEntity<? super TransactionListInCategoryResponseDto> getTransactionsInCategory(
            @RequestParam("categoryId") Long categoryId,
            Pageable pageable
    ) {
        return transactionService.getTransactionsInCategory(categoryId, pageable);
    }

}
