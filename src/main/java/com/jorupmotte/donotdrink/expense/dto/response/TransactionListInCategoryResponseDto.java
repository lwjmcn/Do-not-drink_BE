package com.jorupmotte.donotdrink.expense.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.expense.model.ExpenseCategory;
import com.jorupmotte.donotdrink.expense.model.Transaction;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class TransactionListInCategoryResponseDto extends ResponseDto {
    private final List<TransactionDto> transactions;
    private int totalPage;
    private Long totalElements;

    public TransactionListInCategoryResponseDto(Page<Transaction> pagedTransactions) {
        super();
        this.transactions = pagedTransactions.getContent().stream().map(TransactionDto::from).toList();
        this.totalPage = pagedTransactions.getTotalPages();
        this.totalElements = pagedTransactions.getTotalElements();
    }

    public static ResponseEntity<TransactionListInCategoryResponseDto> success(Page<Transaction> transactions) {
        TransactionListInCategoryResponseDto responseBody = new TransactionListInCategoryResponseDto(transactions);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    private record TransactionDto(Long transactionId, String name, int amount, String datetime, String description){
        public static TransactionDto from(Transaction transaction) {
            return new TransactionDto(
                    transaction.getId(),
                    transaction.getName(),
                    transaction.getAmount().intValue(),
                    transaction.getDate().toString(),
                    transaction.getDescription()
            );
        }
    }
}
