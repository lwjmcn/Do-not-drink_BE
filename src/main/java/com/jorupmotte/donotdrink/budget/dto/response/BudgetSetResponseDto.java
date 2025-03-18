package com.jorupmotte.donotdrink.budget.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BudgetSetResponseDto extends ResponseDto {
    private BudgetSetResponseDto(){
        super();
    }

    public static ResponseEntity<BudgetSetResponseDto> success(){
        BudgetSetResponseDto responseBody = new BudgetSetResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> alreadyDefined(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.BUDGET_ALREADY_DEFINED, ResponseMessage.BUDGET_ALREADY_DEFINED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
