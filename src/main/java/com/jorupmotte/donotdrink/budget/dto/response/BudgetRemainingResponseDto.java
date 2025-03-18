package com.jorupmotte.donotdrink.budget.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.common.type.ResponseCode;
import com.jorupmotte.donotdrink.common.type.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class BudgetRemainingResponseDto extends ResponseDto {
    private final Long remains;

    private BudgetRemainingResponseDto(Long remains){
        super();
        this.remains = remains;
    }
    public static ResponseEntity<BudgetRemainingResponseDto> success(Long remains){
        BudgetRemainingResponseDto responseBody = new BudgetRemainingResponseDto(remains);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noBudget(){
        ResponseDto responseBody = new ResponseDto(ResponseCode.BUDGET_UNDEFINED, ResponseMessage.BUDGET_UNDEFINED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
