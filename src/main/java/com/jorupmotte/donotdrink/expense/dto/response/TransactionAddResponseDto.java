package com.jorupmotte.donotdrink.expense.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TransactionAddResponseDto extends ResponseDto {
    private TransactionAddResponseDto(){
        super();
    }

    public static ResponseEntity<TransactionAddResponseDto> success(){
        TransactionAddResponseDto responseBody = new TransactionAddResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
